(ns sortd.util)

(defn ->str
  "Converts `x` to string.  Passes through `x` `nil` as `nil`."
  [x]
  (when x
    (if (keyword? x)
      (-> x
          str
          (subs 1))
      (str x))))

(defn safe-nth
  "Safe nth for vector/list `v` and index `n`.  Returns `nil` on exception."
  [v n]
  (when v
    (try
      (nth v n)
      (catch Exception _))))

(defn ascending?
  "Determines if numeric sequence `xs` is ascending from first to last."
  [xs]
  (reduce #(if (> % %2)
             (reduced false)
             %2)
          -1
          xs))
