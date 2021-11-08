const express = require('express')
const app = express();
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv/config');

const postsRoute = require('./routes/posts');
const commentsRoute = require('./routes/comments');
const userRoute = require('./routes/users');

app.use(bodyParser.json());
app.use(cors());
app.use('/posts',postsRoute);
app.use('/comments',commentsRoute);
app.use('/users',userRoute);

//Connect to database
mongoose.connect(process.env.DB_CONNECTION,
{ useNewUrlParser: true },
()=> console.log("Connected to the database!")
);

app.listen(8081);