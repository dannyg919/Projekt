Original App Design Project - Projekt
===

# Projekt

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Projekt is a fun Workflow app designed to get the most of your personal and team projects. Layout tasks for your project in a sleek design, where tasks can be sorted by urgency, category, or team. Get a time log report of what tasks have been completed and worked on at the end of the week. Try concentration mode when working on your tasks, where you can work for a specific amount of time (without using your phone!), log it to the task, and earn rewards for your project! 

### App Evaluation

- **Category:** Productivity
- **Mobile:** Catered towards mobile, especially concentration mode which encourages users to not use their devices for a period of time.
- **Story:** Allows users to track project developments in a more exciting way. Can be helpful for solo and team projects.
- **Market:** Anyone who works on project-based works. Individual projects to team project hosted by companies.
- **Habit:** Encourages users to work on specific tasks, take breaks, and check their progress. 
- **Scope:** Allows for pretty standard project management with a few twists such as concentration mode, the rewards this mode brings, and the ability for members to log time into tasks.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* [x] User can create an account for the platform.
* [x] User can log in/out to the platform.
* [x] User can create new projects.
    * [x] Archive/delete Projects
* [x] User can add tasks for each project.
    * [x] Can be prioritized by: Urgency, team, etc. 
* [x] User can complete/remove tasks.
* [x] User can add other users to a project.
* [x] User can add friends.

* [x] User can log time spent on a task and update it to all team members.
* [x] Concentration mode ("Locks" phone when working on a task)


**Optional Nice-to-have Stories**

* [ ] User can collect coins for working on a project task in concentration mode.
* [ ] User can view a leaderboard for the top times logged for the project.
* [ ] User can use collected coins to customize a virtual avatar.
* [ ] User can use collected coins to custimize a virtual project workspace.
* [ ] The ability to turn off the "game" features from the app.
* [ ] User gets a weekly/bi-weekly/monthly overview of how the project is going.
    * [ ] Shows hours logged by team members
    * [ ] Shows tasks completed (late/on-time)
* [ ] User can invite friends to the app.
* [ ] User can create a Project Hierarchy to assign project managers who have the ability to decide when tasks are completed etc.


### 2. Screen Archetypes

* Login
    
    * [ ] User can log in/out to the platform.
* Register
    * [ ] User can create an account for the platform.
* Project Screen
    * [ ] User can create new projects.
    * [ ] User can add other users to a project.
    * [ ] User can create a Project Hierarchy to assign project managers who have the ability to decide when tasks are completed etc.
* Task Screen
    * [ ] User can add tasks for each project.
    * [ ] User can complete/remove tasks.
    
* Time Log Screen
    * [ ] User can log time spent on a task and update it to all team members.
    * [ ] Concentration mode ("Locks" phone when working on a task)
 
* Concentration Mode Screen
    * [ ] Concentration mode ("Locks" phone when working on a task)
* Profile
    * [ ] User can add friends.


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Project Screen 
* Profile
* Home (Task Screen of the last project you were working on)

Optional:

* Store
* Settings


**Flow Navigation** (Screen to Screen)

* Login -> Account creation or home screen (last worked on project or project screen)
* Project Screen -> Task Screen for that project -> 
* Task Screen -> Time Log Screen -(optional)> Concentration Mode Screen


## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://imgur.com/wso5IWk.png" width=700>
<img src="https://imgur.com/Nik6EFr.png" width=700>


### [BONUS] Digital Wireframes & Mockups
<img src="https://imgur.com/yTTfKmn.png" width =700>

### [BONUS] Interactive Prototype

## Schema 
#### Projekt
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the projekt (default field) |
   | createdAt     | DateTime | date when projekt is created (default field) |
   | updatedAt     | DateTime | date when projekt is last updated (default field) |
   | name          | String   | name of projekt |
   | owner         | Pointer to User | owner of projekt |
   | description   | String   | description of projekt |
   | members       | Array | other users working on projekt |
   | image         | File     | image of projekt |
   
#### Card(Group)
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the card (default field) |
   | createdAt     | DateTime | date when card is created (default field) |
   | updatedAt     | DateTime | date when card is last updated (default field) |
   | name          | String   | name of card |
   | projekt         | Pointer to Projekt | projekt where this card belongs |
   

#### Tasks

| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the task (default field) |
   | createdAt     | DateTime | date when task is created (default field) |
   | updatedAt     | DateTime | date when task is last updated (default field) |
   | name          | String   | name of task |
   | card          | Pointer to Card | card where this task belongs |
   | priority      | int      | priority of given task |
    
#### Activity

| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the activity (default field) |
   | createdAt     | DateTime | date when activity is created (default field) |
   | updatedAt     | DateTime | date when activity is last updated (default field) |
   | content       | String   | content of activity |
   | projekt       | Pointer to Task | task where this activity belongs |
   | user          | Pointer to User | user associated to activity |


#### User

| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user (default field) |
   | createdAt     | DateTime | date when user is created (default field) |
   | updatedAt     | DateTime | date when user is last updated (default field) |
   | username      | String   | username |
   | password      | String   | user's password |
   | email         | String   | user's email |
   | firstName     | String   | user's name |
   | lastName      | String   | user's name |
   | friendsList   | Array    | user's friend's IDs |
   | coins         | int   | number of coins user has to spend at store |


### Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='ProjektGIF.gif' title='Video Walkthrough' width='200px' alt='Video Walkthrough' />

GIF created with [Kap](https://getkap.co/).

