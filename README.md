# How to Run This Project (Frontend + Java Backend)

This project has two parts:

1. **Frontend** – HTML, CSS, JS (index.html, style.css, script.js)
2. **Backend** – Java file (EnvServer.java) that gives data at `/data`

GitHub cannot run Java backend.  
So we host backend and frontend separately using **Render**.

Below are the exact steps.

---

# 1️ How to Deploy the Java Backend (EnvServer.java)

### Step 1: Go to Render dashboard
https://dashboard.render.com

### Step 2: Click "New" → "Web Service"

### Step 3: Connect your GitHub repository

### Step 4: Select "Docker" as Runtime

### Step 5: Make sure these files exist in your repo:
- Dockerfile  
- build.sh  
- start.sh  
- EnvServer.java  

### Step 6: Deploy
Render will create a backe
