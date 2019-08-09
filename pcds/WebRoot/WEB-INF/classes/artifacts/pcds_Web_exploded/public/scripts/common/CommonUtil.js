var map = new Object();  
map.put = function (key,value){
    var s = "map." + key + ' = "' + value + '";';  
    eval(s);  
}
map.get = function(key){
    var v = eval("map." + key + ";");  
    return v;  
}  
  
map.keySet = function(){
    var keySets = new Array();  
    for(key in map){  
        if(!(typeof(map[key])=="function")){  
            keySets.push(key);  
        }  
    }  
    return keySets;  
} 
