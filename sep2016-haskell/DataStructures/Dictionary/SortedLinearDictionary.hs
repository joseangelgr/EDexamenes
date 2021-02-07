-------------------------------------------------------------------------------
-- Sorted wrt keys, Linear implementation of dictionaries
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2015
-------------------------------------------------------------------------------

module DataStructures.Dictionary.SortedLinearDictionary
  ( Dictionary
  , empty
  , isEmpty
  , size
  , insert
  , updateOrInsert
  , valueOf
  , isDefinedAt
  , delete

  , keys
  , values
  , keysValues

  , foldKeys
  , foldValues
  , foldKeysValues
  ) where

import Data.List(intercalate)
import Data.Maybe(isJust)
import Test.QuickCheck

data Dictionary a b = Empty | Node a b (Dictionary a b)

empty :: Dictionary a b
empty  = Empty

isEmpty :: Dictionary a b -> Bool
isEmpty Empty  = True
isEmpty _      = False

size :: Dictionary a b -> Int
size Empty         = 0
size (Node _ _ d)  = 1 + size d

insert :: (Ord a) => a -> b -> Dictionary a b -> Dictionary a b
insert k v Empty  = Node k v Empty
insert k v d@(Node k' v' d')
  | k < k'        = Node k v d
  | k == k'       = Node k v d'
  | otherwise     = Node k' v' (insert k v d')

updateOrInsert :: (Ord a) => a -> (b -> b) -> b -> Dictionary a b -> Dictionary a b
updateOrInsert k f v Empty  = Node k v Empty
updateOrInsert k f v d@(Node k' v' d')
  | k < k'                  = Node k v d
  | k == k'                 = Node k (f v') d'
  | otherwise               = Node k' v' (updateOrInsert k f v d')

valueOf :: (Ord a) => a -> Dictionary a b -> Maybe b
valueOf k Empty  = Nothing
valueOf k (Node k' v' d')
  | k < k'       = Nothing
  | k == k'      = Just v'
  | otherwise    = valueOf k d'

isDefinedAt :: (Ord a) => a -> Dictionary a b -> Bool
isDefinedAt k d  = isJust (valueOf k d)

delete :: (Ord a) => a -> Dictionary a b -> Dictionary a b
delete k Empty  = Empty
delete k d@(Node k' v' d')
  | k < k'      = d
  | k == k'     = d'
  | otherwise   = Node k' v' (delete k d')

keys :: Dictionary a b -> [a]
keys d  = foldKeys (:) [] d

values :: Dictionary a b -> [b]
values d  = foldValues (:) [] d

keysValues :: Dictionary a b -> [(a,b)]
keysValues d  = foldKeysValues (\k v xs -> (k,v) : xs) [] d

foldKeys :: (a -> c -> c) -> c -> Dictionary a b -> c
foldKeys f z Empty         = z
foldKeys f z (Node k v d)  = f k (foldKeys f z d)

foldValues :: (b -> c -> c) -> c -> Dictionary a b -> c
foldValues f z Empty         = z
foldValues f z (Node k v d)  = f v (foldValues f z d)

foldKeysValues :: (a -> b -> c -> c) -> c -> Dictionary a b -> c
foldKeysValues f z Empty         = z
foldKeysValues f z (Node k v d)  = f k v (foldKeysValues f z d)

instance (Show a, Show b) => Show (Dictionary a b) where
  show d  = "SortedLinearDictionary(" ++ intercalate "," (aux d) ++ ")"
   where
    aux Empty         = []
    aux (Node k v d)  = (show k++"->"++show v) : aux d

instance (Eq a, Eq b) => Eq (Dictionary a b) where
  Empty      == Empty         = True
  Node k v d == Node k' v' d' = k==k' && v==v' && d==d'
  _          == _             = False

instance (Ord a, Arbitrary a, Arbitrary b) => Arbitrary (Dictionary a b) where
    arbitrary  = do
      kvs <- listOf arbitrary
      return (foldr (\(k,v) -> insert k v) empty kvs)
