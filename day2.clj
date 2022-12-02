(ns day2
  (:require
   [clojure.string :as str]))

(def input (->>
            (slurp "day2.input")
            str/split-lines
            (map (fn [[x _ y]]
                   [(- (int x) 64)
                    (- (int y) 87)]))))

(defn get-score [s1 s2]
  (-> (- s2 s1) (mod 3) {0 3 1 6 2 0} (+ s2)))

(defn get-sign-to-play [r s1]
  (as->
   ({1 dec 2 identity 3 inc} r) %
    (% s1)
    (get {0 3 4 1} % %)))

;; Q1

(def answer1 (->> input
                  (map (partial apply get-score))
                  (apply +)))

;; Q2

(def answer2 (->> input
                  (map (fn [[s1 r]]
                         [s1 (get-sign-to-play r s1)]))
                  (map (partial apply get-score))
                  (apply +)))