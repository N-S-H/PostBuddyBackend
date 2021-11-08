
module.exports = function () {
 
   this.randomNumber = function(currentNumbers) {
   var id;
   let unique=false;
   while(unique===false) {
   id = Math.floor((Math.random() * 10000000) + 1);
   var count=0;
   currentNumbers.forEach((v) => (v===id && count++))
   if(count==0) unique=true;
   }
  return id;
}

}
