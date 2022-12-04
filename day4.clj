(ns day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def input
  (->> (slurp "day4.input")
       str/split-lines
       (map (fn [s]
              (->> (str/split s #",|-")
                   (map #(Integer/parseInt %))
                   (partition 2))))))

(defn range->set [x y] (set (range x (inc y))))

(defn subset? [[x y] [x' y']] (some true? ((juxt set/subset? set/superset?) (range->set x y) (range->set x' y'))))

;; Q1

(def answer1
  (->> input
       (filter #(apply subset? %))
       count))

;; Q2

(def answer2
  (->> input
       (map (fn [[[x y] [x' y']]] (set/intersection (range->set x y) (range->set x' y'))))
       (remove empty?)
       count))
