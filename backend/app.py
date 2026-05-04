from flask import Flask, request, jsonify
from flask_cors import CORS
import pickle
import numpy as np
import os

app = Flask(__name__)
# Enable CORS so the PHP frontend can communicate with this Python API
CORS(app)  

# Define the path for the saved AI model
MODEL_PATH = 'model.pkl'

# Check if the model exists and load it
if os.path.exists(MODEL_PATH):
    with open(MODEL_PATH, 'rb') as f:
        model = pickle.load(f)
    print("Model loaded successfully!")
else:
    print("Error: model.pkl not found. Please run train_model.py first.")

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get JSON data from the request
        data = request.get_json()
        
        # Validation: Ensure all required fields are present and not empty
        required_fields = ['glucose', 'bp', 'bmi', 'age']
        for field in required_fields:
            if field not in data or str(data[field]).strip() == "":
                return jsonify({
                    'status': 'error', 
                    'message': f'Missing or empty field: {field}'
                }), 400

        # Convert input data into a NumPy array for the model
        # Input order must match the training data: glucose, bp, bmi, age
        features = np.array([[
            float(data['glucose']), 
            float(data['bp']), 
            float(data['bmi']), 
            float(data['age'])
        ]])
        
        # Generate prediction using the loaded model
        prediction = model.predict(features)
        
        # Interpret results: 1 = High Risk, 0 = Normal / Low Risk
        result = "High Risk" if prediction[0] == 1 else "Normal / Low Risk"
        
        return jsonify({
            'status': 'success',
            'diagnosis': result
        })
    
    except Exception as e:
        return jsonify({
            'status': 'error', 
            'message': str(e)
        }), 500

if __name__ == '__main__':
    # Start the Flask development server on port 5000
    app.run(port=5000, debug=True)