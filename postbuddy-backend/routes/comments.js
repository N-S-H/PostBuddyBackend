const express = require('express')
const router = express.Router();
const Comment = require('../models/Comment');
require('../Utils/random_util.js')();

router.post('/',async (req,res) => {

    var comments = [];

    try {
        comments = await Comment.find();
        }catch(err) {
            res.json({message: err});
        } 
        

    const id = generateUniqueCommentId(comments);
    const comment = new Comment({
         _id: id,
         postedby: req.body.postedby,
         postid: req.body.postid,
         content: req.body.content
    });
    
    try {
        const savedComment = await comment.save();
        res.json(savedComment);
    }catch(err) {
        res.json({message: err});
    }
});

router.get('/', async (req,res)=> {
    try {
    const comments = await Comment.find();
    res.json(comments);
    }catch(err) {
        res.json({message: err});
    }

});

router.get('/:commentId', async (req,res)=> {
    try {
        const commentId = req.params.commentId;
        const comment = await Comment.findById(commentId);
        res.json(comment);
    }catch(err) {
        res.json({message: err});
    }
})

router.patch('/:commentId', async (req,res)=> {
    try {
        const commentId = req.params.commentId;
        const updated_content = req.body.content;
        const updatedComment = await Comment.updateOne({_id: commentId},
            {$set: {content: updated_content}} 
            );
            res.json(updatedComment);
    }catch(err) {
        res.json({message: err});
    }
})

router.delete('/:commentId', async (req,res)=> {
    try {
        const commentId = req.params.commentId;
        const deletedComment = await Comment.findByIdAndDelete(commentId);
        res.json(deletedComment);
    }catch(err) {
        res.json({message: err});
    }
})

router.get('/post/:postId', async (req,res)=> {
     try {
         const postId = req.params.postId;
         const comments = await Comment.find();
         var required_comments = new Array();
         for(var i=0;i<comments.length;i++) {
             if(comments[i].postid == postId) {
                 required_comments.push(comments[i]);
             }
         }
         res.json(required_comments);
     }catch(err) {
         res.json({message: err});
     }
})



function generateUniqueCommentId(comments) {
    if(comments.length==0) return Math.floor((Math.random() * 10000000) + 1)
    try {
        var currentIds = new Array();
        for(var i=0; i<comments.length;i++) {
            currentIds.push(comments[i]._id)
            return randomNumber(currentIds);
        }
        }catch(err) {
            console.log({message: err});
        }
     return null;
 }

module.exports = router;