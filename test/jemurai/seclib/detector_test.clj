(ns jemurai.seclib.detector-test
  (:require [clojure.test :refer :all]
            [jemurai.seclib.detector :refer :all]))

(deftest general-test
  (testing "Test that detector sees xss and sql correctly."
    (and
     (is (= true (detect-on-string "mkonda@yo.com<script>alert(3)</script>")))
     (is (= false (detect-on-string "mkonda@jemurai.com")))
     (is (= false (detect-on-string "mkonda@jemurai.com")))
     (is (= true (detect-on-string "mkonda@jemurai.com; drop table users;")))
     (is (= true (detect-on-string "mkonda@jemurai.com or 1=1"))))))

(deftest xss-test
  (testing "Test that detector sees xss correctly."
    (and
     (is (= true (detect looks-like-xss? "mkonda@yo.com<script>alert(3)</script>")))
     (is (= false (detect looks-like-xss? "mkonda@jemurai.com"))))))

(deftest sqli-test
  (testing "Test that detector sees slqi correctly."
    (and
     (is (= true (detect looks-like-sqli? "mkonda@yo.com--;<script>alert(3)</script>")))
     (is (= false (detect looks-like-sqli? "mkonda@jemurai.com")))
     (is (= true (detect looks-like-sqli? "mkonda@jemurai.com; drop table users;")))
     (is (= true (detect looks-like-sqli? "mkonda@jemurai.com or 1=1")))
     (is (= true (detect looks-like-sqli? "mkonda@jemurai.com or \"1\"=\"1\""))))))
