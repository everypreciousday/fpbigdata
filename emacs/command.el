(defun my-first-command()
  (interactive)
  (message "Hello Emacs"))

(defun string-arg-command(arg)
  (interactive "sName?")
  (message "Hello %s" arg))

(defun region-arg-command (start end)
  (interactive "r")
  (message "From %d to %d" start end))

(defun delete-current-line ()
  (interactive)
  (beginning-of-line)
  (kill-line)
  (kill-line))

(global-set-key
 (kbd "C-x C-d")
 delete-current-line)

(defun copy-line (arg)
  "Copy lines"
  (interactive "p")
  (kill-ring-save
   (line-beginning-position)
   (line-beginning-position (+ 1 arg)))
  (message "%d line%s copied"
           arg
           (if (= 1 arg) "" "s")))


