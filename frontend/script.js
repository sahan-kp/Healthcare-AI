let editId = null;

// LOADER

window.onload = function () {

    setTimeout(function () {

        document.getElementById('loader').style.opacity = '0';

        setTimeout(() => {

            document.getElementById('loader').style.display = 'none';

            document.getElementById('main-content').style.display = 'block';

            loadUsers();

        }, 500);

    }, 3000);

};

// ADD / UPDATE

async function processHealthCheck() {

    const userData = {

        id: editId,

        name: document.getElementById("name").value,
        email: document.getElementById("email").value,

        age: document.getElementById("age").value,
        heartRate: document.getElementById("heartRate").value,

        bp: document.getElementById("bp").value,
        sugar: document.getElementById("sugar").value,

        bmi: document.getElementById("bmi").value,

        symptoms: document.getElementById("symptoms").value

    };

    if (!userData.name || !userData.email) {

        alert("Please enter Name and Email");

        return;

    }

    // AI PART

    try {

        const aiResponse = await fetch("http://127.0.0.1:5000/predict", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({

                glucose: userData.sugar,
                bp: userData.bp,
                bmi: userData.bmi,
                age: userData.age

            })

        });

        const aiResult = await aiResponse.json();

        const box = document.getElementById("ai-result-box");

        box.style.display = "block";

        box.className =
            aiResult.diagnosis === "High Risk"
                ? "alert alert-danger shadow-sm"
                : "alert alert-success shadow-sm";

        box.innerHTML =
            `<h4>AI Diagnosis : ${aiResult.diagnosis}</h4>`;

    } catch (e) {

        console.log("Flask server not running");

    }

    // DATABASE SAVE

    let url = editId
        ? "http://localhost:8080/update"
        : "http://localhost:8080/add";

    fetch(url, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(userData)

    })

    .then(res => {

        if (res.ok) {

            alert(editId ? "Updated!" : "Saved!");

            clearForm();

            editId = null;

            loadUsers();

        }

    });

}

// LOAD USERS

function loadUsers() {

    fetch("http://localhost:8080/all")

    .then(res => res.json())

    .then(data => {

        showUsers(data);

    });

}

// SHOW USERS

function showUsers(data) {

    const list = document.getElementById("userList");

    list.innerHTML = "";

    data.reverse().forEach(user => {

        const div = document.createElement("div");

        div.className = "user-card shadow-sm";

        div.innerHTML = `

            <div class="d-flex justify-content-between">

                <strong class="text-primary">
                    ${user.name}
                </strong>

                <span class="badge bg-light text-dark">
                    ${user.age || 'N/A'} Yrs
                </span>

            </div>

            <div class="small mt-2">

                <b>Email:</b> ${user.email}<br>

                <b>BP:</b> ${user.bp || '-'} |

                <b>Sugar:</b> ${user.sugar || '-'} |

                <b>BMI:</b> ${user.bmi || '-'} |

                <b>BPM:</b> ${user.heartRate || '-'}<br>

                <span class="text-muted">
                    ${user.symptoms || ''}
                </span>

            </div>

            <div class="mt-3">

                <button class="btn btn-warning btn-sm"
                    onclick='editUser(${JSON.stringify(user)})'>

                    Edit

                </button>

                <button class="btn btn-danger btn-sm"
                    onclick='deleteUser(${user.id})'>

                    Delete

                </button>

            </div>

        `;

        list.appendChild(div);

    });

}

// DELETE

function deleteUser(id) {

    if (confirm("Delete this record?")) {

        fetch(`http://localhost:8080/delete/${id}`, {

            method: "POST"

        })

        .then(() => {

            loadUsers();

        });

    }

}

// EDIT

function editUser(user) {

    editId = user.id;

    document.getElementById("name").value = user.name;
    document.getElementById("email").value = user.email;

    document.getElementById("age").value = user.age;
    document.getElementById("heartRate").value = user.heartRate;

    document.getElementById("bp").value = user.bp;
    document.getElementById("sugar").value = user.sugar;

    document.getElementById("bmi").value = user.bmi;

    document.getElementById("symptoms").value = user.symptoms;

}

// SEARCH

function searchUser() {

    let text = document.getElementById("searchText").value;

    if (text === "") {

        loadUsers();

        return;

    }

    fetch(`http://localhost:8080/search/${text}`)

    .then(res => res.json())

    .then(data => {

        showUsers(data);

    });

}

// CLEAR FORM

function clearForm() {

    document.querySelectorAll("input, textarea")
        .forEach(i => i.value = "");

}