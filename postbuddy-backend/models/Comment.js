const mongoose = require('mongoose');

const Commentschema = mongoose.Schema({
    _id: {
        type: Number,
        required: true
    },
    postedby: {
        type: String,
        required: true
    },
    postid: {
        type: Number,
        required: true 
    },
    content: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('comments',Commentschema)