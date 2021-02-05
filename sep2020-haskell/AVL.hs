{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL
  (
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight]

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin c = B c []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c ws) = c

addObject :: Weight -> Bin -> Bin
addObject w (B c ws)
  | w > c = error "El peso supera la capacidad"
  | otherwise = B (c-w) (w:ws)

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node bin h cp lt rt) = max cp (max (maxRemainingCapacity lt) (maxRemainingCapacity rt))

height :: AVL -> Int
height Empty = 0
height (Node bin h cp lf rg)
  | height lf > height rg = 1 + height lf
  | otherwise = 1 + height rg



nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight (B c ws) h lf rg = Node (B c ws) h (max c (max (maxRemainingCapacity lf) (maxRemainingCapacity rg))) lf rg


node :: Bin -> AVL -> AVL -> AVL
node (B c ws) lf rg = Node (B c ws) h cp lf rg
  where
    h = 1 + max (height lf) (height rg)
    cp = max c (max (maxRemainingCapacity lf) (maxRemainingCapacity rg))

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft c l (Node x _ _ r1 r2) =  node x (node c l r1) r2

addNewBin :: Bin -> AVL -> AVL
addNewBin b arbol = aux b arbol
  where
    aux b' Empty = node b' Empty Empty
    aux b' (Node bin _ _ lg rg)
      | (height rg' - height lg) > 1 = rotateLeft bin lg rg'
      | otherwise = nodeWithHeight bin (1 + height rg') lg rg'
      where
        rg' = aux b' rg
--preguntar

addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst w peso Empty = addNewBin (B (w-peso) [peso]) Empty
addFirst w peso arbol@(Node bin@(B c ws) h capacidad l r)
-- Si al avl no le queda capacidad restante añade un nuevo bin
  | maxRemainingCapacity arbol < peso = addNewBin  (addObject peso (emptyBin w)) arbol
-- en otro caso busca el bin en el que puede meter el objeto
  | otherwise = aux peso arbol
  where
    aux ob (Node b1 h1 _ l1 r1)
      | maxRemainingCapacity l1 >= ob = nodeWithHeight b1 h1 (aux ob l1) r1--addFirst w peso l
      | remainingCapacity b1 >= ob = nodeWithHeight (addObject ob b1) h1 l1 r1--node (addObject peso (B c ws)) l r
      | otherwise = nodeWithHeight b1 h1 r1 (aux ob r1)--addFirst w peso r

addAll:: Capacity -> [Weight] -> AVL
addAll cap (w:ws) = aux cap (w:ws) Empty
  where
    aux cap [] arbol = arbol
    aux cap (w:ws) arbol = aux cap ws (addFirst cap w arbol)

toList :: AVL -> [Bin]
toList a = list a []
  where
    list Empty xs = xs
    list (Node bin h c lf rg) xs = list lf (bin : list rg xs)

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL
addAllFold _ _ = undefined




{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os


instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"
