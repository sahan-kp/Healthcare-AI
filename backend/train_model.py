import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
import pickle

# 1. Load data from CSV file
try:
    df = pd.read_csv('diabetes_data.csv')
    print("CSV file loaded successfully.")
except FileNotFoundError:
    print("Error: 'diabetes_data.csv' not found. Please check the folder.")
    exit()

# 2. Separate Features (X) and Target (y)
# We use glucose, bp, bmi, and age to predict diabetes
X = df[['glucose', 'bp', 'bmi', 'age']]
y = df['diabetes']

# 3. Train the AI Model
# Using RandomForestClassifier which is widely used in the industry
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X, y)

# 4. Save the trained model as 'model.pkl'
with open('model.pkl', 'wb') as f:
    pickle.dump(model, f)

print("AI Model trained successfully and saved as 'model.pkl'!")