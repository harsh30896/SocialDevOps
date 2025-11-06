# Render Deployment - Quick Answers

## ✅ FRONTEND STATUS: 100% COMPLETE

All 5 frontend pages are fully implemented:
- ✅ Login page
- ✅ Registration page  
- ✅ Feed page (posts)
- ✅ Friends page (friend requests)
- ✅ Messages page (chat)

## Render Setup Questions & Answers

When Render asks you questions, use these answers:

### 1. **Service Type**
   → **Web Service**

### 2. **Environment**
   → **Java** (or select "Other" if Java not listed)

### 3. **Build Command**
   ```
   ./mvnw clean package -DskipTests
   ```
   OR if that doesn't work:
   ```
   mvn clean package -DskipTests
   ```

### 4. **Start Command**
   ```
   java -jar target/testApp-0.0.1-SNAPSHOT.jar
   ```

### 5. **Environment Variables** (if asked to add manually)
   Add these one by one:
   - `JAVA_VERSION` = `21`
   - `DATABASE_URL` = `jdbc:postgresql://apli13.h.filess.io:61006/SocialApp_ourluckme`
   - `DATABASE_USERNAME` = `SocialApp_ourluckme`
   - `DATABASE_PASSWORD` = `cc8fa453452e27ea46edae80f5f650f8077efde0`
   - `SHOW_SQL` = `false`

### 6. **Health Check Path** (if asked)
   → `/login`

### 7. **Port** (if asked)
   → Leave empty or `8080` (Render auto-sets PORT env var)

## Easiest Method: Use render.yaml

If you have `render.yaml` in your repo root, Render will auto-detect it and use those settings. Just:
1. Push code to GitHub/GitLab
2. Connect repo to Render
3. Select "Blueprint" deployment
4. Render will use `render.yaml` automatically!

## After Deployment

- Your app URL will be: `https://your-app-name.onrender.com`
- First visit creates database tables automatically
- Start by registering: `https://your-app-name.onrender.com/register`

