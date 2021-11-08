#Description 

The “PostBuddy” web application is a text posts sharing platform similar to Reddit and Hackernews.
Functionality for all the users
•	A user can view all the posts and the comments associated with it
•	A user can register into the system through sign up facility
•	A user, if already registered, can login to the system 
Functionality for the registered users
•	A user can write a post
•	A user can edit the posts which were written by self, previously, but the posts cannot be deleted
•	A user can comment on any of the posts, including the self-written posts and the user can delete his/her own comments on any of the posts
•	A user can only view the posts or comments written by other users
•	A user can logout of the system. After that, the user can only view any posts or comments.

#Installations
Install MongoDB in the system 
--> create database 'postbuddy' 
---> Import collections files (.json format) in database folder 

Install node version>=12
Install npm version>=6

#Commands to run front-end application (navigate to the application folder)
1. npm install 
2. npm install --save react-router-dom
3. npm install axios
4. npm install @material-ui/core
5. npm install @material-ui/icons
6. npm install @material-ui/lab 
7. npm start 

--> ReactJS Application will start at port 3000


#Commands to run back-end application (navigate to the application folder)
1. npm install 
2. npm install mongoose
3. npm install express
4. npm install cors
5. npm install nodemon
6. npm install dotenv
7. npm install body-parser 
8. npm start 

---> NodeJs Application will start at 8081

We can exercise API calls independent of front-end application through an API collaboration platform called "postman"


#docker build image and run the containers

1.For front-end application

->Command to build the docker image:
docker build -t postbuddyfrontend/react-app .

->Command to run the docker container: 
docker run -d -it  -p 3000:3000/tcp --name react-app postbuddyfrontend/react-app:latest

2.For back-end application

->Command to build the docker image:
docker build -t postbuddybackend/node-web-app .

->Command to run the docker container: 
docker run -p 8081:8081 -d postbuddybackend/node-web-app

