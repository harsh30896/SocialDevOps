# Testing Guide - Social Media App

## 🌐 Live URL: https://social-ed8q.onrender.com

## Test Checklist

### ✅ 1. Registration Test
- [ ] Go to `/register`
- [ ] Fill form and create account
- [ ] Should redirect to login page

### ✅ 2. Login Test
- [ ] Go to `/login`
- [ ] Enter credentials
- [ ] Should redirect to feed page (`/`)

### ✅ 3. Post Creation Test
- [ ] Create a post on feed page
- [ ] Post should appear in feed
- [ ] Check timestamp is displayed

### ✅ 4. Friend Request Test
- [ ] Create 2 different users (use incognito for second)
- [ ] Send friend request from User 1 to User 2
- [ ] Login as User 2, go to `/friends`
- [ ] See incoming request
- [ ] Accept request

### ✅ 5. Messages Test
- [ ] Go to `/messages`
- [ ] Select a user
- [ ] Send message
- [ ] Message should appear in chat
- [ ] Test from both users

### ✅ 6. Feed with Friends
- [ ] After accepting friend request
- [ ] Create post as friend
- [ ] Login as main user
- [ ] Check feed shows friend's posts

## Test Users (Create these)

**User 1:**
- Display Name: Test User
- Username: testuser
- Password: test123

**User 2:**
- Display Name: Friend User
- Username: frienduser
- Password: test123

## Expected Behavior

✅ All pages should load without errors
✅ Navigation links should work
✅ Forms should submit successfully
✅ Database should persist data
✅ Friend requests should work
✅ Messages should be saved
✅ Feed should show posts from friends

## Troubleshooting

**If tables don't exist:**
- Just visit any page, Hibernate will auto-create them

**If login fails:**
- Check username/password are correct
- Try registering again

**If friend request doesn't appear:**
- Make sure you're logged in as the recipient
- Check `/friends` page

**If messages don't show:**
- Make sure you selected a user from the list
- Check both users can see the conversation

