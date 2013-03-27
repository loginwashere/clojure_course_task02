(ns clojure-course-task02.core
  (:import java.io.File)
  (:gen-class))

(def files
    (agent []))

(defn addFile [files file]
    (vec (conj files file)))

(defn sendFile [files file]
    (send files addFile file))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (doall
    (pmap
      (fn [file]
        (future
          (if
            (not
              (empty?
                (re-find
                  (re-pattern file-name) (.getName file))))
            (sendFile files (.getName file)))))
      (file-seq (clojure.java.io/file path))))
  (deref files))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
