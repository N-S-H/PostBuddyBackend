const express = require('express')
const router = express.Router();
const Post = require('../models/Post');
require('../Utils/random_util.js')();

router.post('/',async (req,res) => {

    var posts = [];

    try {
        posts = await Post.find();
        }catch(err) {
            res.json({message: err});
        }    
    const id = generateUniquePostId(posts);
    const date = generateCurrentDate();
    console.log(date);
    const post = new Post({
         _id: id,
         postedby: req.body.postedby,
         content: req.body.content,
         commentcount: req.body.commentcount,
         postdate: date
    });
    
    try {
        const savedPost = await post.save();
        res.json(savedPost);
    }catch(err) {
        res.json({message: err});
    }
});

router.get('/', async (req,res)=> {
    try {
    const posts = await Post.find();
    res.json(posts);
    }catch(err) {
        res.json({message: err});
    }

});

router.get('/:postId', async (req,res)=> {
    try {
        const postId = req.params.postId;
        const post = await Post.findById(postId);
        res.json(post);
    }catch(err) {
        res.json({message: err});
    }
})

router.patch('/updateContent', async (req,res)=> {
    try {
        const postId = req.body.postid;
        const updated_content = req.body.content;
        const updatedPost = await Post.updateOne({_id: postId},
            {$set: {content: updated_content}} 
            );
            res.json(updatedPost);
    }catch(err) {
        res.json({message: err});
    }
})

router.patch('/decreaseCommentCount/:postId', async(req,res)=> {
    try {
        const postId = req.params.postId;
        const post = await Post.findById(postId);
        var currentCommentCount = post.commentcount;
        currentCommentCount--;
        const updatedPost = await Post.updateOne({_id:postId},
            {$set: {commentcount: currentCommentCount}}
            );
            res.json(updatedPost);
    }catch(err) {
        res.json({message: err})
    }
})

router.patch('/increaseCommentCount/:postId', async(req,res)=> {
    try {
        const postId = req.params.postId;
        const post = await Post.findById(postId);
        var currentCommentCount = post.commentcount;
        currentCommentCount++;
        const updatedPost = await Post.updateOne({_id:postId},
            {$set: {commentcount: currentCommentCount}}
            );
            res.json(updatedPost);
    }catch(err) {
        res.json({message: err})
    }
})



function generateUniquePostId(posts) {
    if(posts.length==0) return Math.floor((Math.random() * 10000000) + 1)
    try {
        var currentIds = new Array();
        for(var i=0; i<posts.length;i++) {
            currentIds.push(posts[i]._id)
            return randomNumber(currentIds);
        }
        }catch(err) {
            console.log({message: err});
        }
     return null;
 }

 function generateCurrentDate() {
     try {
    var date = new Date();
    var months = ["January", "February", "March", "April", 
    "May", "June", "July", "August", "September", "October", 
    "November", "December"];
    var day = date.getDate();
    var month = months[date.getMonth()];
    var year = date.getFullYear();
    const current_date = day + " " + month+ " "+year;
    return current_date;
     }
     catch(err) {
         console.log({message: err});
     }
     return null;
 }

module.exports = router;