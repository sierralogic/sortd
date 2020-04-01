(ns sortd.test.conf)

(def csv-filename "data/records.csv")
(def empty-filename "data/records.empty.csv")
(def pipe-filename "data/records.pipe.txt")
(def space-filename "data/records.space.txt")

(def expected-sorted-by-gender-last-names ["Sexton" "Shamaya" "Bukowski" "Wright"])
(def expected-sorted-by-dob-last-names ["Wright" "Bukowski" "Sexton" "Shamaya"])
(def expected-sorted-by-name-last-names ["Bukowski" "Sexton" "Shamaya" "Wright"])
