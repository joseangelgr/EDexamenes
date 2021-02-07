-------------------------------------------------------------------------------
-- Queue implemented with two stacks
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2015
-------------------------------------------------------------------------------

module DataStructures.Queue.TwoStacksQueue
  ( Queue
  , empty
  , isEmpty
  , enqueue
  , first
  , dequeue
  ) where

import Data.List(intercalate)
import Test.QuickCheck
import qualified DataStructures.Stack.LinearStack as S

data Queue a = TSQ (S.Stack a) (S.Stack a)

-- forces this invariant: first stack is only empty if queue is empty
mkValid :: S.Stack a -> S.Stack a -> Queue a
mkValid s1 s2
  | S.isEmpty s1 = TSQ (revFromOnto s2 s1) S.empty
  | otherwise    = TSQ s1 s2
  where
    revFromOnto s2 s1
      | S.isEmpty s2 = s1
      | otherwise    = revFromOnto (S.pop s2) (S.push (S.top s2) s1)

empty :: Queue a
empty = TSQ S.empty S.empty

isEmpty :: Queue a -> Bool
isEmpty (TSQ s1 s2) = S.isEmpty s1 -- correctness depends on invariant

enqueue :: a -> Queue a -> Queue a
enqueue x q@(TSQ s1 s2) = mkValid s1 (S.push x s2)

dequeue :: Queue a -> Queue a
dequeue q@(TSQ s1 s2)
  | isEmpty q = error "dequeue on empty queue"
  | otherwise = mkValid (S.pop s1) s2

first :: Queue a -> a
first q@(TSQ s1 s2)
  | isEmpty q = error "first on empty queue"
  | otherwise = S.top s1  -- correctness depends on invariant

-- Showing a queue
instance (Show a) => Show (Queue a) where
  show q = "TwoStacksQueue(" ++ intercalate "," (aux q) ++ ")"
    where
        aux q
          | isEmpty q = []
          | otherwise = show (first q) : aux (dequeue q)

-- Queues equality
instance (Eq a) => Eq (Queue a) where
  q1 == q2
    | e1 && e2         = True
    | not e1 && not e2 = first q1 == first q2 && dequeue q1 == dequeue q2
    | otherwise        = False
    where
      e1 = isEmpty q1
      e2 = isEmpty q2

-- This instance is used by QuickCheck to generate random queues
instance (Arbitrary a) => Arbitrary (Queue a) where
    arbitrary = do
      xs <- listOf arbitrary
      return (foldr enqueue empty xs)
