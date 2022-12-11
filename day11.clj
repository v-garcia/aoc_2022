(ns day11
  (:require [clojure.string :as str]))

(defn extract-numbers [str] 
  (mapv #(Integer/parseInt %) (rest (clojure.string/split str #"[^0-9]+"))))

(def input
  (->> (slurp "day11.input")
       (str/split-lines)
       (partition-all 7)
       (mapv (fn [[_ l2 l3 l4 l5 l6]]
               {:items (extract-numbers l2),
                :op (as-> (re-find #"[+*]" l3) op ({"*" *, "+" +} op) (if-let [x (first (extract-numbers l3))] (partial op x) #(op % %))),
                :test? #(zero? (mod % (first (extract-numbers l4)))),
                :next {true (first (extract-numbers l5)), false (first (extract-numbers l6))}}))))


(defn round
  [monkeys]
  (let [cool-down #(int (Math/floor (/ % 3)))]
    (->> (reduce-kv (fn [monkeys i {:keys [:op :test? :next]}]
                      (-> (reduce (fn [monkeys item]
                                    (as-> item item
                                         (op item)
                                         (cool-down item)
                                         (update-in monkeys [(next (test? item)) :items] conj item)))
                                  monkeys
                                  (get-in monkeys [i :items]))
                          (update i (fn [{:keys [:items], :as m}] (update m :inspect-count (fnil + 0) (count items))))
                          (assoc-in [i :items] [])))
                    monkeys
                    monkeys))))

(def answer1
  (->> ((apply comp (repeat 20 round)) input)
       (map :inspect-count)
       sort
       reverse
       (take 2)
       (apply *)
       ))