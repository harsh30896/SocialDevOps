# Render Deployment Guide

## Frontend Status: ✅ COMPLETE

All frontend pages are implemented and ready:
- ✅ Login page (`login.html`)
- ✅ Registration page (`register.html`)
- ✅ Feed page (`feed.html`) - Create and view posts
- ✅ Friends page (`friends.html`) - Send/accept friend requests
- ✅ Messages page (`messages.html`) - Chat interface

## Render Deployment Steps

### Option 1: Using render.yaml (Recommended)

1. **Push your code to GitHub/GitLab/Bitbucket**
   ```bash
   git add .
   git commit -m "Ready for Render deployment"
   git push
   ```

2. **Go to Render Dashboard**
   - Visit https://dashboard.render.com
   - Click "New +" → "Blueprint"

3. **Connect Repository**
   - Connect your Git repository
   - Render will automatically detect `render.yaml`

4. **Deploy**
   - Render will use the configuration from `render.yaml`
   - No additional setup needed!

### Option 2: Manual Setup

If Render asks questions, use these answers:

1. **Service Type**: Web Service

2. **Build Command**: 
   ```
   ./mvnw clean package -DskipTests
   ```

3. **Start Command**: 
   ```
   java -jar target/testApp-0.0.1-SNAPSHOT.jar
   ```

4. **Environment Variables** (if asked):
   ```
   JAVA_VERSION=21
   DATABASE_URL=jdbc:postgresql://apli13.h.filess.io:61006/SocialApp_ourluckme
   DATABASE_USERNAME=SocialApp_ourluckme
   DATABASE_PASSWORD=cc8fa453452e27ea46edae80f5f650f8077efde0
   SHOW_SQL=false
   ```

5. **Health Check Path**: `/login`

6. **Port**: Render will auto-detect (usually 8080)

## Important Notes

- ✅ Frontend is 100% complete with all 5 pages
- ✅ Database connection is configured
- ✅ All dependencies are in `pom.xml`
- ✅ Application will auto-create database tables on first run
- ✅ Environment variables are set up for security

## After Deployment

1. Your app will be available at: `https://your-app-name.onrender.com`
2. First visit will create all database tables automatically
3. Start by registering a new user at `/register`
4. All features (posts, friends, messages) will work!

## Troubleshooting

- If build fails: Check that Java 21 is available
- If database connection fails: Verify environment variables are set
- If pages don't load: Check that Thymeleaf templates are in `src/main/resources/templates/`

