(ns day1
  (:require
   [clojure.string :as str]))

(def input (->
            (slurp "day1.input")
            (str/split #"\n\n")
            (->>
             (map (fn [l] (->> (str/split-lines l) (map #(Integer/parseInt %))))))))

;; Q1
(def answer1 (->> input
                  (map (partial apply +))
                  sort
                  last))

;; Q2
(def answer2 (->> input
                  (map (partial apply +))
                  sort
                  (take-last 3)
                  (apply +)))