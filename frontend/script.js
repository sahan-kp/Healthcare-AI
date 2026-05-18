let editId = null;

// LOADER

window.onload = function () {

    setTimeout(function () {

        document.getElementById("loader").style.display = "none";

        document.getElementById("main-content").style.display = "block";

        loadUsers();

    },3000);

};

// OPEN HISTORY

function openHistoryPopup(){

    document.getElementById("historyPopup")
        .style.display = "flex";

    loadUsers();

}

// CLOSE HISTORY

function closeHistoryPopup(){

    document.getElementById("historyPopup")
        .style.display = "none";

}

// ADD / UPDATE

async function processHealthCheck(){

    const userData = {

        id: editId,

        name:
            document.getElementById("name").value,

        email:
            document.getElementById("email").value,

        age:
            document.getElementById("age").value,

        heartRate:
            document.getElementById("heartRate").value,

        bp:
            document.getElementById("bp").value,

        sugar:
            document.getElementById("sugar").value,

        bmi:
            document.getElementById("bmi").value,

        symptoms:
            document.getElementById("symptoms").value

    };

    if(userData.name === "" ||
       userData.email === ""){

        alert("Please Enter Name & Email");

        return;

    }

    // AI PART

    try{

        const aiResponse = await fetch(

            "http://127.0.0.1:5000/predict",

            {

                method:"POST",

                headers:{
                    "Content-Type":"application/json"
                },

                body:JSON.stringify({

                    glucose:userData.sugar,
                    bp:userData.bp,
                    bmi:userData.bmi,
                    age:userData.age

                })

            }

        );

        const aiResult = await aiResponse.json();

        const box =
            document.getElementById("ai-result-box");

        box.style.display = "block";

        box.className =
            aiResult.diagnosis === "High Risk"
            ? "alert alert-danger"
            : "alert alert-success";

        box.innerHTML =
            `AI Diagnosis : ${aiResult.diagnosis}`;

    }

    catch(error){

        console.log(error);

    }

    // SAVE / UPDATE

    let url = editId
        ? "http://localhost:8080/update"
        : "http://localhost:8080/add";

    fetch(url,{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify(userData)

    })

    .then(res=>{

        if(res.ok){

            alert(editId
                ? "Patient Updated"
                : "Patient Saved");

            clearForm();

            editId = null;

            loadUsers();

        }

    });

}

// LOAD USERS

function loadUsers(){

    fetch("http://localhost:8080/all")

    .then(res=>res.json())

    .then(data=>{

        showUsers(data);

    });

}

// SHOW USERS

function showUsers(data){

    const list =
        document.getElementById("userList");

    list.innerHTML = "";

    data.reverse().forEach(user=>{

        list.innerHTML += `

        <div class="user-card">

            <div class="d-flex justify-content-between">

                <h5 class="text-primary">

                    👤 ${user.name}

                </h5>

                <span>

                    ${user.age || 'N/A'} Years

                </span>

            </div>

            <hr>

            <p>

                <b>Email :</b>
                ${user.email}

            </p>

            <p>

                <b>BP :</b>
                ${user.bp || '-'}

            </p>

            <p>

                <b>Sugar :</b>
                ${user.sugar || '-'}

            </p>

            <p>

                <b>BMI :</b>
                ${user.bmi || '-'}

            </p>

            <p>

                <b>BPM :</b>
                ${user.heartRate || '-'}

            </p>

            <p>

                <b>Symptoms :</b>
                ${user.symptoms || '-'}

            </p>

            <button class="action-btn edit-btn"
                    onclick='editUser(${JSON.stringify(user)})'>

                Edit

            </button>

            <button class="action-btn delete-btn"
                    onclick='deleteUser(${user.id})'>

                Delete

            </button>

        </div>

        `;

    });

}

// DELETE

function deleteUser(id){

    if(confirm("Delete this record?")){

        fetch(

            `http://localhost:8080/delete/${id}`,

            {

                method:"POST"

            }

        )

        .then(()=>{

            loadUsers();

        });

    }

}

// EDIT

function editUser(user){

    editId = user.id;

    document.getElementById("name").value =
        user.name;

    document.getElementById("email").value =
        user.email;

    document.getElementById("age").value =
        user.age;

    document.getElementById("heartRate").value =
        user.heartRate;

    document.getElementById("bp").value =
        user.bp;

    document.getElementById("sugar").value =
        user.sugar;

    document.getElementById("bmi").value =
        user.bmi;

    document.getElementById("symptoms").value =
        user.symptoms;

    closeHistoryPopup();

    window.scrollTo({

        top:0,
        behavior:"smooth"

    });

}

// SEARCH

function searchUser(){

    let text =
        document.getElementById("searchText").value;

    if(text === ""){

        loadUsers();

        return;

    }

    fetch(

        `http://localhost:8080/search/${text}`

    )

    .then(res=>res.json())

    .then(data=>{

        showUsers(data);

    });

}

// CLEAR FORM

function clearForm(){

    document.querySelectorAll(
        "input, textarea"
    )

    .forEach(input=>{

        input.value = "";

    });

}