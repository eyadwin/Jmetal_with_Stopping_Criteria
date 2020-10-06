write("", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex",append=FALSE)
resultDirectory<-"000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/data"
latexHeader <- function() {
  write("\\documentclass{article}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\title{StandardStudy}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\usepackage{amssymb}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{document}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\maketitle", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\section{Tables}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\caption{", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(problem, "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(".HV.}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)

  write("\\label{Table:", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(problem, "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(".HV.}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)

  write("\\centering", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{scriptsize}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{tabular}{", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(tabularString, "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(latexTableFirstLine, "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\hline ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTableTail <- function() { 
  write("\\hline", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{tabular}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{scriptsize}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{table}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
}

printTableLine <- function(indicator, algorithm1, algorithm2, i, j, problem) { 
  file1<-paste(resultDirectory, algorithm1, sep="/")
  file1<-paste(file1, problem, sep="/")
  file1<-paste(file1, indicator, sep="/")
  data1<-scan(file1)
  file2<-paste(resultDirectory, algorithm2, sep="/")
  file2<-paste(file2, problem, sep="/")
  file2<-paste(file2, indicator, sep="/")
  data2<-scan(file2)
  if (i == j) {
    write("--", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  }
  else if (i < j) {
    if (wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) >= median(data2)) {
        write("$\\blacktriangle$", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE) 
      }
    }
    else {
      write("--", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
  }
}

### START OF SCRIPT 
# Constants
problemList <-c("UF1", "UF2", "UF3", "UF4", "UF5", "UF6", "UF7", "UF8", "UF9", "UF10") 
algorithmList <-c("MOEAD_NONE", "MOEAD_MOVING_AVG", "MOEAD_MGBM", "MOEAD_MGBM2", "MOEAD_OCD_HV", "Harmony_MOEAD_NONE", "Harmony_MOEAD_MOVING_AVG", "Harmony_MOEAD_MGBM", "Harmony_MOEAD_MGBM2", "Harmony_MOEAD_OCD_HV", "Harmony_Hybrid_MOEAD_NONE", "Harmony_Hybrid_MOEAD_MOVING_AVG", "Harmony_Hybrid_MOEAD_MGBM", "Harmony_Hybrid_MOEAD_MGBM2", "Harmony_Hybrid_MOEAD_OCD_HV") 
tabularString <-c("lcccccccccccccc") 
latexTableFirstLine <-c("\\hline  & MOEAD_MOVING_AVG & MOEAD_MGBM & MOEAD_MGBM2 & MOEAD_OCD_HV & Harmony_MOEAD_NONE & Harmony_MOEAD_MOVING_AVG & Harmony_MOEAD_MGBM & Harmony_MOEAD_MGBM2 & Harmony_MOEAD_OCD_HV & Harmony_Hybrid_MOEAD_NONE & Harmony_Hybrid_MOEAD_MOVING_AVG & Harmony_Hybrid_MOEAD_MGBM & Harmony_Hybrid_MOEAD_MGBM2 & Harmony_Hybrid_MOEAD_OCD_HV\\\\ ") 
indicator<-"HV"

 # Step 1.  Writes the latex header
latexHeader()
# Step 2. Problem loop 
for (problem in problemList) {
  latexTableHeader(problem,  tabularString, latexTableFirstLine)

  indx = 0
  for (i in algorithmList) {
    if (i != "Harmony_Hybrid_MOEAD_OCD_HV") {
      write(i , "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
      write(" & ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
      jndx = 0 
      for (j in algorithmList) {
        if (jndx != 0) {
          if (indx != jndx) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
          if (j != "Harmony_Hybrid_MOEAD_OCD_HV") {
            write(" & ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
          else {
            write(" \\\\ ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
        }
        jndx = jndx + 1
      }
      indx = indx + 1
    }
  }

  latexTableTail()
} # for problem

tabularString <-c("| l | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}  p{0.15cm}   | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{10}{c|}{MOEAD_MOVING_AVG} & \\multicolumn{10}{c|}{MOEAD_MGBM} & \\multicolumn{10}{c|}{MOEAD_MGBM2} & \\multicolumn{10}{c|}{MOEAD_OCD_HV} & \\multicolumn{10}{c|}{Harmony_MOEAD_NONE} & \\multicolumn{10}{c|}{Harmony_MOEAD_MOVING_AVG} & \\multicolumn{10}{c|}{Harmony_MOEAD_MGBM} & \\multicolumn{10}{c|}{Harmony_MOEAD_MGBM2} & \\multicolumn{10}{c|}{Harmony_MOEAD_OCD_HV} & \\multicolumn{10}{c|}{Harmony_Hybrid_MOEAD_NONE} & \\multicolumn{10}{c|}{Harmony_Hybrid_MOEAD_MOVING_AVG} & \\multicolumn{10}{c|}{Harmony_Hybrid_MOEAD_MGBM} & \\multicolumn{10}{c|}{Harmony_Hybrid_MOEAD_MGBM2} & \\multicolumn{10}{c|}{Harmony_Hybrid_MOEAD_OCD_HV} \\\\") 

# Step 3. Problem loop 
latexTableHeader("UF1 UF2 UF3 UF4 UF5 UF6 UF7 UF8 UF9 UF10 ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "Harmony_Hybrid_MOEAD_OCD_HV") {
    write(i , "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
    write(" & ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
          } 
          if (problem == "UF10") {
            if (j == "Harmony_Hybrid_MOEAD_OCD_HV") {
              write(" \\\\ ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
            } 
            else {
              write(" & ", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
            }
          }
     else {
    write("&", "000__Output\000__Results__MOEAD_V11\Study_All_methods_V11/R/Problems.HV.Wilcox.tex", append=TRUE)
     }
        }
      }
      jndx = jndx + 1
    }
    indx = indx + 1
  }
} # for algorithm

  latexTableTail()

#Step 3. Writes the end of latex file 
latexTail()

