(ns recursion)

(defn product [coll]
  (if (empty? coll)
    1
    (* (first coll)
       (product (rest coll)))))

(defn singleton? [coll]
  (and (not (empty? coll))
       (empty? (rest coll))))

(defn my-last [coll]
  (if (empty? (rest coll))
    (first coll)
    (my-last (rest coll))))

(defn max-element [a-seq]
  (if (empty? (rest a-seq))
    (first a-seq)
    (max (first a-seq) (max-element (rest a-seq)))))

(defn seq-max [seq-1 seq-2]
  (let [seq1-len (count seq-1)
        seq2-len (count seq-2)]
    (if (> seq1-len seq2-len) seq-1 seq-2)))

(defn longest-sequence [a-seq]
  (if (empty? (rest a-seq))
    (first a-seq)
    (seq-max (first a-seq) (longest-sequence (rest a-seq)))))

(defn my-filter [pred? a-seq]
  (if (empty? a-seq)
    a-seq
    (if (pred? (first a-seq))
      (cons (first a-seq) (my-filter pred? (rest a-seq)))
      (my-filter pred? (rest a-seq)))))

(defn sequence-contains? [elem a-seq]
  (cond
    (empty? a-seq) false
    (= elem (first a-seq)) true
    :else (sequence-contains? elem (rest a-seq))))

(defn my-take-while [pred? a-seq]
  (cond
    (empty? a-seq) a-seq
    (not (pred? (first a-seq))) '()
    :else
      (cons (first a-seq) (my-take-while pred? (rest a-seq)))))

(defn my-drop-while [pred? a-seq]
  (cond
    (empty? a-seq) a-seq
    (not (pred? (first a-seq))) a-seq
    :else (my-drop-while pred? (rest a-seq))))

(defn seq= [a-seq b-seq]
  (cond
    (and (empty? a-seq) (empty? b-seq)) true
    (or (empty? a-seq) (empty? b-seq)) false
    (not (= (first a-seq) (first b-seq))) false
    :else (seq= (rest a-seq) (rest b-seq))))

(defn my-map [f seq-1 seq-2]
  (if (or (empty? seq-1) (empty? seq-2))
    '()
    (cons (f (first seq-1) (first seq-2))
          (my-map f (rest seq-1) (rest seq-2)))))

(defn power [n k]
  (if (zero? k)
    1
    (* n (power n (dec k)))))

(defn fib [n]
  (cond
    (zero? n) 0
    (= n 1) 1
    :else (+ (fib (dec n)) (fib (- n 2)))))

(defn my-repeat [how-many-times what-to-repeat]
  (if (<= how-many-times 0)
    '()
    (cons what-to-repeat (my-repeat (dec how-many-times) what-to-repeat))))

(defn my-range [up-to]
  (if (<= up-to 0)
    '()
    (cons (dec up-to) (my-range (dec up-to)))))

(defn tails [a-seq]
  (if (empty? a-seq)
    (cons '() a-seq)
    (cons (seq a-seq) (tails (rest a-seq)))))

(defn inits [a-seq]
  (reverse (map reverse (tails (reverse a-seq)))))

(defn rotations [a-seq]
  (distinct (map concat (tails a-seq) (inits a-seq))))

(defn my-frequencies-helper [freqs a-seq]
  (if (empty? a-seq)
    freqs
    (let [elem (first a-seq)
          freq (if (contains? freqs elem) (inc (get freqs elem)) 1)
          new-freqs (assoc freqs elem freq)]
      (my-frequencies-helper new-freqs (rest a-seq)))))

(defn my-frequencies [a-seq]
  (my-frequencies-helper {} a-seq))

(defn un-frequencies [a-map]
  (if (empty? a-map)
    '()
    (let [elem-freq (first a-map)
          elem (first elem-freq)
          freq (second elem-freq)
          new-seq (repeat freq elem)]
      (concat new-seq (un-frequencies (rest a-map))))))

(defn my-take [n coll]
  (if (or (zero? n) (empty? coll))
    '()
    (cons (first coll) (my-take (dec n) (rest coll)))))

(defn my-drop [n coll]
  (if (or (zero? n) (empty? coll))
    coll
    (my-drop (dec n) (rest coll))))

(defn halve [a-seq]
  (let [seq-len (count a-seq)
        half (int (/ seq-len 2))
        first-half (my-take half a-seq)
        tail-len (- seq-len (count first-half))
        tail-half (my-drop (- seq-len tail-len) a-seq)]
    (cons first-half (cons tail-half '()))))

(defn seq-merge [a-seq b-seq]
  (cond
    (and (not (empty? a-seq)) (empty? b-seq)) a-seq
    (and (empty? a-seq) (not (empty? b-seq))) b-seq
    (<= (first a-seq) (first b-seq)) (cons (first a-seq) (seq-merge (rest a-seq) b-seq))
    :else (cons (first b-seq) (seq-merge a-seq (rest b-seq)))))

(defn merge-sort [a-seq]
  (if (<= 0 (count a-seq) 1)
    a-seq
    (let [[first-half second-half] (halve a-seq)]
      (seq-merge (merge-sort first-half) (merge-sort second-half)))))

(defn split-into-monotonics [a-seq]
  (if (empty? a-seq)
    a-seq
    (let [increasing-filter (fn [x] (apply <= (seq x)))
          decreasing-filter (fn [x] (apply >= (seq x)))
          inits-seqs (rest (inits a-seq))
          incr-mono-seqs (filter increasing-filter inits-seqs)
          decr-mono-seqs (filter decreasing-filter inits-seqs)
          longest-incr (last incr-mono-seqs)
          longest-decr (last decr-mono-seqs)
          num-incr (count incr-mono-seqs)
          num-decr (count decr-mono-seqs)]
      (if (<= num-decr num-incr)
        (cons longest-incr (split-into-monotonics (drop num-incr a-seq)))
        (cons longest-decr (split-into-monotonics (drop num-decr a-seq)))))))

(defn permutations [a-set]
  (if (empty? a-set)
    (list a-set)
    (mapcat
      (fn [x]
        (map (fn [y] (cons x y))
             (permutations (remove (fn [z] (= x z)) a-set))))
      a-set)))

(defn powerset [a-set]
  (if (empty? a-set)
    #{#{}}
    (let [prev (powerset (set (rest a-set)))]
      (set
        (concat
          (map (fn [elt] (set (cons (first a-set) elt))) prev)
          prev)))))
