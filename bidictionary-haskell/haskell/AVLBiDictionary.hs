-------------------------------------------------------------------------------
-- Apellidos, Nombre: 
-- Titulacion, Grupo: 
--
-- Estructuras de Datos. Grados en Informatica. UMA.
-------------------------------------------------------------------------------

module AVLBiDictionary( BiDictionary
                      , empty
                      , isEmpty
                      , size
                      , insert
                      , valueOf
                      , keyOf
                      , deleteByKey
                      , deleteByValue
                      , toBiDictionary
                      , compose
                      , isPermutation
                      , orbitOf
                      , cyclesOf
                      ) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.Set.BSTSet               as S

import           Data.List                               (intercalate, nub,
                                                          (\\))
import           Data.Maybe                              (fromJust, fromMaybe,
                                                          isJust)
import           Test.QuickCheck


data BiDictionary a b = Bi (D.Dictionary a b) (D.Dictionary b a)

-- | Exercise a. empty, isEmpty, size

empty :: (Ord a, Ord b) => BiDictionary a b
empty _ = Bi (D.empty) (D.empty)

isEmpty :: (Ord a, Ord b) => BiDictionary a b -> Bool
isEmpty (Bi dk dv)
  | D.isEmpty dk == True = True
  | D.isEmpty dv == True = True
  | otherwise = False

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi dk dv) = D.size dk

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert k v (Bi dk dv)
  | D.isDefinedAt k dk == False = (Bi (D.insert k v dk) (D.insert v k dv))
  | otherwise = insert k v (Bi dk' dv')
    where
      dk' = D.delete k dk
      dv' = D.delete (fromJust (D.valueOf k dv)) dv--no podria hacerse D.delete v dv directamente?


-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf k empty = Nothing--mejorable ver sol
valueOf k (Bi dk dv) = D.valueOf k dk

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf k dic@(Bi a b)
  | D.isDefinedAt k b = D.valueOf k b
  | otherwise = Nothing

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k (Bi dk dv)
  | D.isDefinedAt k dk = Bi (D.delete k dk) (D.delete (valueOf k dk) dv)
  | otherwise = (Bi dk dv)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v (Bi dk dv)
  | D.isDefinedAt v dv = Bi (D.delete (keyOf v) dk) (D.delete v dv)
  | otherwise = (Bi dk dv)

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary dic
  | length(D.keysValues dic) /= length(D.keys dic) = error "no es inyectivo"
  |otherwise = aux (D.keysValues dic) empty
    where
      aux [] res = res
      aux [(a,b):ls] res = aux ls (insert a b res) 

-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose (Bi dk1 dv1) (Bi dk2 dv2) = aux (D.keysValues dk1) (Bi dk1 dv1) (Bi dk2 dv2) empty
  where
    aux [] _ _ res = res
    aux [(k,v):ls] (Bi dk1 dv1) (Bi dk2 dv2) res
      | (valueOf k (Bi dk1 dv1)) == v && (valueOf v dv1) == k && D.isDefinedAt v dk2 && (D.isDefinedAt (valueOf v dk2) dv2) = aux ls (Bi dk1 dv1) (Bi dk2 dv2) (insert k (valueOf v dk2) res)

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi dk dv) = aux (Bi dk dv) (D.keysValues dk)
  where
    aux _ [] = True
    aux (Bi dk dv) [(k,v):ls]
      | D.isDefinedAt k dv && D.isDefinedAt v dk = aux (Bi dk dv) ls  
      | otherwise = false


-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf = undefined

-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf = undefined

-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
