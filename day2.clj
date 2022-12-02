(ns day2
  (:require
   [clojure.string :as str]))

(def input (->>
            (slurp "day2.input")
            str/split-lines
            (map (fn [[x _ y]]
                   [(- (int x) 65)
                    (- (int y) 88)]))))

(defn get-score [s1 s2]
  (-> (- s2 s1) (mod 3) {0 3 1 6 2 0} (+ s2 1)))

(defn what-to-play [r s1]
  (mod (+ -1 r s1) 3))

;; Q1

(def answer1 (->> input
                  (map (partial apply get-score))
                  (apply +)))

;; Q2

(def answer2 (->> input
                  (map (fn [[s1 r]]
                         [s1 (what-to-play r s1)]))
                  (map (partial apply get-score))
                  (apply +)))