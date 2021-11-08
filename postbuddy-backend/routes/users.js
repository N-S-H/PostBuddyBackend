const express = require('express')
const router = express.Router();
const User = require('../models/User');

router.post('/',async (req,res) => {

    const users = await User.find();
    const userNameStatus = uniqueUserName(req.body.username,users);
    if(userNameStatus) {
    const user = new User({
         username: req.body.username,
         password: req.body.password,
         firstname: req.body.firstname,
         lastname: req.body.lastname,
         email: req.body.email
    });

    try {
        const savedUser = await user.save();
        res.json(true);
    }catch(err) {
        res.json({message: err});
    }
   }
   res.json(false);
});

router.get('/login/:username/:password', async (req,res)=> {
    
    const username = req.params.username;
    const password = req.params.password;
    try {
    const users = await User.find();
    for(var i=0;i<users.length;i++) {
        if(users[i].username == username && users[i].password == password) {
            res.json(true);
        }
    }
    } catch(err) {
        res.json({message: err});
    }
    res.json(false);
});

function uniqueUserName(username,users) {
    try {
    
    for(var i=0;i<users.length;i++) {
        if(users[i].username === username) {
            return false;
        }
    }
    } catch(err) {
        console.log(err);
        return false;
    }
    return true;
}

router.get('/:username', async (req,res)=> {
    const username = req.params.username;
    try {
    const users = await User.find();
    for(var i=0;i<users.length;i++) {
        if(users[i].username === username) {
            res.json(users[i]);
        }
    }
    } catch(err) {
        res.json({message: err});
    }
    res.json(null);
})

module.exports = router;