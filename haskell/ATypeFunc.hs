data A = One String Int | Two | Three 

aFunc :: A -> String 
aFunc (One str int) = str  
aFunc Two = "two"
aFunc Three = "three"
