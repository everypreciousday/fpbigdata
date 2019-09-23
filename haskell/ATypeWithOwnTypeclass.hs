data A = One | Two | Three

class ATypeClass a where 
  myOp :: a -> a -> String

instance ATypeClass A where 
  myOp One One = "One and One!"
  myOp _ _ = "Not (One and One)!"
