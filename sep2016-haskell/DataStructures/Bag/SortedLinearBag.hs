-------------------------------------------------------------------------------
-- Linear implementation of Bag. Nodes are sorted and 0 occurences nodes are
-- removed from the structure.
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2012
-------------------------------------------------------------------------------

module DataStructures.Bag.SortedLinearBag
  ( Bag
  , empty
  , isEmpty
  , size
  , insert
  , delete
  , occurrences

  , fold
  , foldOcc

  , union
  , intersection
  , difference
  ) where

import Data.List(intercalate)
import Test.QuickCheck

data Bag a  = Empty | Node a Int (Bag a)  -- the second component stores number of ocurrences

empty :: Bag a
empty  = Empty

isEmpty :: Bag a -> Bool
isEmpty Empty  = True
isEmpty _      = False

insert :: (Ord a) => a -> Bag a -> Bag a
insert x Empty  = Node x 1 Empty
insert x (Node y n b)
  | x<y         = Node x 1 (Node y n b)
  | x==y        = Node y (n+1) b
  | otherwise   = Node y n (insert x b)

occurrences :: (Ord a) => a -> Bag a -> Int
occurrences x Empty  = 0
occurrences x (Node y n b)
  | x<y              = 0
  | x==y             = n
  | otherwise        = occurrences x b

delete :: (Ord a) => a -> Bag a -> Bag a
delete x Empty  = Empty
delete x (Node y n b)
  | x<y         = Node y n b
  | x==y        = if n>1 then Node y (n-1) b else b
  | otherwise   = Node y n (delete x b)

size :: Bag a -> Int
size Empty         = 0
size (Node _ n b)  = n + size b

fold :: (a -> b -> b) -> b -> Bag a -> b
fold f z  = fun
 where
  fun Empty         = z
  fun (Node x 0 b)  = fun b
  fun (Node x n b)  = f x (fun (Node x (n-1) b))

foldOcc :: (a -> Int -> b -> b) -> b -> Bag a -> b
foldOcc f z  = fun
 where
  fun Empty         = z
  fun (Node x n b)  = f x n (fun b)

union :: (Ord a) => Bag a -> Bag a -> Bag a
union Empty b' = b'
union b Empty  = b
union nd@(Node x n b) nd'@(Node x' n' b')
  | x == x'    = Node x (n+n') (union b b')
  | x < x'     = Node x n (union b nd')
  | otherwise  = Node x' n' (union nd b')

intersection :: (Ord a) => Bag a -> Bag a -> Bag a
intersection Empty _  = Empty
intersection _ Empty  = Empty
intersection nd@(Node x n b) nd'@(Node x' n' b')
  | x == x'    = Node x (min n n') (intersection b b')
  | x < x'     = intersection b nd'
  | otherwise  = intersection nd b'

difference :: (Ord a) => Bag a -> Bag a -> Bag a
difference Empty _  = Empty
difference b Empty  = b
difference nd@(Node x n b) nd'@(Node x' n' b')
  | x == x'    = (if n > n' then Node x (n-n') else id) (difference b b')
  | x < x'     = Node x n (difference b nd')
  | otherwise  = difference nd b'

instance (Show a) => Show (Bag a) where
        show b  = "SortedLinearBag(" ++ intercalate "," (map show (elems b)) ++")"
          where
            elems Empty         = []
            elems (Node x n b)  = replicate n x ++ elems b

-- Bag equality (assumes nodes are sorted and occurrences > 0)
instance (Eq a) => Eq (Bag a) where
  Empty        == Empty           = True
  (Node x n b) ==(Node x' n' b')  = x==x' && n==n' && b==b'
  _            == _               = False

instance (Ord a, Arbitrary a) => Arbitrary (Bag a) where
    arbitrary  = do
      xs <- listOf arbitrary
      return (foldr insert empty xs)
