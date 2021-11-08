const mongoose = require('mongoose');

const Postschema = mongoose.Schema({
    _id: {
        type: Number,
        required: true
    },
    postedby: {
        type: String,
        required: true
    },
    content: {
        type: String,
        required: true 
    },
    commentcount: {
        type: Number,
        required: true
    },
    postdate: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('posts',Postschema)
