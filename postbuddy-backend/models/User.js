const mongoose = require('mongoose');

const Userschema = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    firstname: {
        type: String,
        required: true 
    },
    lastname: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: false
    }
});

module.exports = mongoose.model('users',Userschema)