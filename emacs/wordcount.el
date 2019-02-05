(defun get-content ()
  (buffer-substring 1 (buffer-size)))

(defun get-word-list ()
  (split-string (get-content)))

(defun updateMap (word hashmap)
  (if (null (gethash word hashmap))
      (puthash word 1 hashmap)
    (puthash word (+ 1 (gethash word hashmap)) hashmap)))

(defun get-word-count-table (word-list result)
  (cond
   ((null word-list) result)
   ((< 0 (length word-list))
    (updateMap (car word-list) result)
    (get-word-count-table (cdr word-list) result))))

(defun xah-hash-to-list (hash-table)
  (let (result)
    (maphash
     (lambda (k v) (push (list k v) result))
     hash-table)
    result))


(defun my-word-count ()
  (interactive)
  (message "%s"
	   (xah-hash-to-list
	    (get-word-count-table
	     (get-word-list)
	     (make-hash-table :test 'equal)))))
