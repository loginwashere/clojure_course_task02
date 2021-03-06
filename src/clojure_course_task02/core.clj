(ns clojure-course-task02.core
  (:import java.io.File)
  (:gen-class))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (let [files (agent [])
        pattern (re-pattern file-name)]
    (dorun
      (pmap
        (fn [file]
          (when (re-find pattern (.getName file))
            (send files conj (.getName file))))
        (file-seq (clojure.java.io/file path))))
    (await files)
    @files))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
