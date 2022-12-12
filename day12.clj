(ns day12
  (:require [clojure.string :as str]))

(def input
  (->> (slurp "day12.input")
       (str/split-lines)))

(def line-length (count (first input)))

(def flat-input (vec (apply concat input)))

(def p->height (assoc
                (into {} (map (fn [i] [(char i) (- i 97)]) (range (int \a) (inc (int \z)))))
                \S 0
                \E 25))

(def max-step 1)

(defn pos->surrounding [i] (vec (filter
                                 #(<= 0 % (dec (count flat-input)))
                                 [(- i line-length)
                                  (inc i)
                                  (+ i line-length)
                                  (dec i)])))

(def start-position (.indexOf flat-input \S))

(def end-position (.indexOf flat-input \E))

(def i->height (comp p->height flat-input))

(defn  pos->available-surrounding [i]
  (let [height   (i->height i)]
    (filterv #(<= (Math/abs (- height (i->height %))) max-step) (pos->surrounding i))))

(defn step [paths]
  (mapcat (fn [[p :as l]]
            (if (= end-position p) (list l)
                (->> (pos->available-surrounding p)
                     (remove (set l))
                     (map (partial conj l)))))
          paths))

;; (def question-1 (->>
;;                  (loop [m (step  (list (list start-position)))]
;;                    (if (every? (comp #{end-position} first) m)
;;                      m
;;                      (recur (step m))))
;;                  (sort-by count)
;;                  first
;;                  count
;;                  dec))
