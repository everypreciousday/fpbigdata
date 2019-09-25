;define function
(defun sum (x y)
  "sum x and y"
  (+ x y))

(sum 1 2)

;define global variable

(defvar *x* 123)
(defvar *x* 500)

(defparameter *y* 123)
(defparameter *y* 500)

(+ *x* 3)
(+ *y* 3)

;local variable

(let ((x 1))
  (+ x 1))


(let ((x 1)
      (y 2))
  (+ x y))

(let* ((x 1)
       (y (+ x 3)))
  (+ x y))

;if 

(defvar x 123)
(if (> x 3)
    "yes!"
  "no")

(defun compare3 (x)
  (if (> x 3)
      "bigger"
    "smaller"))

;if and progn

(defvar x 123)
(if (> x 3)
    (progn
      (print "yes")
      (print "and this too!"))
  (print "no"))


;cond

(defvar x 12)
(cond
 ((< x 0) "minus")
 ((> x 10) "over 10")
 (t "else"))


;CAR and CDR of list

(CAR '(1 2 3))
(CDR '(1 2 3))

(CAR
 (CDR
  '(1 2 3)))


(CAR
 (CDR
  (CDR
   '(1 2 3))))

;cons and append

(cons 1 '(2 3))
(append '(1 2) '(3 4))
(cons '(1 2) '(3 4))

;recursive function

(defun myself (x)
  (if (> x 10)
      "finish"
    (progn
      (print x)
      (myself (+ 1 x)))))

(myself 0)

;recursive function 

(defun what-is-recursive? (i n)
  (if (>= i n)
      (print "I said enough, didn't I?")
    (progn
      (print "One day a student asked professor. 'What is recursive function? So he answered like this ")
      (what-is-recursive? (+ 1 i) n))))

(what-is-recursive? 0 5)

;recursive function - fibonacci number

(defun fibo (n)
  (cond
   ((= n 1) 1)
   ((= n 2) 1)
   (t (+
       (fibo (- n 1))
       (fibo (- n 2))))))

;recursive function - sum of list

(defun sum-of-list (input-list)
  (if (null input-list)
      0
    (+
     (CAR input-list)
     (sum-of-list (CDR input-list)))))

(sum-of-list '(1 2 3))

;recursive function - print list 

(defun print-all (input-list)
  (if (null input-list)
      "finish"
    (progn
      (print (CAR input-list))
      (print-all (CDR input-list)))))

(print-all '(1 2 3))

;recursive function - max of list

(defun max-of-list (input-list)
  (cond
   ((null input-list) "NON SENSE")
   ((= 1 (list-length input-list)) (CAR input-list))
   ((> 1 (list-length input-list))
    (max
     (CAR input-list)
     (max-of-list (CDR input-list))))))

(max-of-list '(3 1 8 2 6))

;recursive function - returning list 

(defun add-one-to-list (input-list)
  (if (null input-list)
      NIL
    (cons
     (+ 1 (CAR input-list))
     (add-one-to-list (CDR input-list)))))

;lambda expression

(lambda (x) (+ x 1))
((lambda (x) (+ x 1)) 1)

;higher-order function

(mapcar
 (lambda (x) (+ x 1))
 '(1 2 3))

(remove-if
 (lambda (x) (= x 2))
 '(1 2 3))

(reduce
 (lambda (x y) (+ x y))
 '(1 2 3 4 5))

