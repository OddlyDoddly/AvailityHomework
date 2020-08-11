var lispCode = "(defun fibonacci (N)\n" +
    "  \"Compute the N'th Fibonacci number.\"\n" +
    "  (if (or (zerop N) (= N 1))\n" +
    "      1\n" +
    "    (let\n" +
    "\t((F1 (fibonacci (- N 1)))\n" +
    "\t (F2 (fibonacci (- N 2))))\n" +
    "      (+ F1 F2))))";

let unclosedParens = 0;
for(let i = 0; i < lispCode.length; ++i) {
    let char = lispCode.charAt(i);

    if(char === '(') {
        unclosedParens++;
    } else if(char === ')') {
        unclosedParens--;
    }
}

if(unclosedParens === 0) {
    console.log("All parens have been properly opened and closed")
} else if(unclosedParens < 0) {
    console.log("You have " + Math.abs(unclosedParens)  + " closed parenthesis which do not have a matching opening parenthesis");
} else {
    console.log("You have " + unclosedParens  + " open parenthesis which do not have a matching closing parenthesis");
}