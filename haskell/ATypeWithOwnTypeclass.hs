data A = One | Two | Three

instance Show A where
  show One = "1"
  show Two = "2"
  show Three = "3"

instance Eq A where
  One == One = True
  Two == Two = True
  Three == Three = True
  _ == _ = False

instance Ord A where
   One <= One = True
   One <= Two = True
   One <= Three = True

   Two <= Two = True
   Two <= Three = True

   Three <= Three = True
   _ <= _ = False

class ATypeClass a where 
  myOp :: a -> a -> String

instance ATypeClass A where 
  myOp One One = "One and One!"
  myOp _ _ = "Not (One and One)!"
