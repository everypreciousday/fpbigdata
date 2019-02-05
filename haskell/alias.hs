type Age = Int
type BornYear = Int
type CurrentYear = Int


transform :: BornYear -> CurrentYear -> Age
transform born current = current - born
