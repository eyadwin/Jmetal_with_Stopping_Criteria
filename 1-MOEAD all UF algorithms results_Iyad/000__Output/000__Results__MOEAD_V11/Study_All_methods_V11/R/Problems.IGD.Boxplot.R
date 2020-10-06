postscript("Problems.IGD.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data/"
qIndicator <- function(indicator, problem)
{
fileMOEAD_NONE<-paste(resultDirectory, "MOEAD_NONE", sep="/")
fileMOEAD_NONE<-paste(fileMOEAD_NONE, problem, sep="/")
fileMOEAD_NONE<-paste(fileMOEAD_NONE, indicator, sep="/")
MOEAD_NONE<-scan(fileMOEAD_NONE)

fileMOEAD_MOVING_AVG<-paste(resultDirectory, "MOEAD_MOVING_AVG", sep="/")
fileMOEAD_MOVING_AVG<-paste(fileMOEAD_MOVING_AVG, problem, sep="/")
fileMOEAD_MOVING_AVG<-paste(fileMOEAD_MOVING_AVG, indicator, sep="/")
MOEAD_MOVING_AVG<-scan(fileMOEAD_MOVING_AVG)

fileMOEAD_MGBM<-paste(resultDirectory, "MOEAD_MGBM", sep="/")
fileMOEAD_MGBM<-paste(fileMOEAD_MGBM, problem, sep="/")
fileMOEAD_MGBM<-paste(fileMOEAD_MGBM, indicator, sep="/")
MOEAD_MGBM<-scan(fileMOEAD_MGBM)

fileMOEAD_MGBM2<-paste(resultDirectory, "MOEAD_MGBM2", sep="/")
fileMOEAD_MGBM2<-paste(fileMOEAD_MGBM2, problem, sep="/")
fileMOEAD_MGBM2<-paste(fileMOEAD_MGBM2, indicator, sep="/")
MOEAD_MGBM2<-scan(fileMOEAD_MGBM2)

fileMOEAD_OCD_HV<-paste(resultDirectory, "MOEAD_OCD_HV", sep="/")
fileMOEAD_OCD_HV<-paste(fileMOEAD_OCD_HV, problem, sep="/")
fileMOEAD_OCD_HV<-paste(fileMOEAD_OCD_HV, indicator, sep="/")
MOEAD_OCD_HV<-scan(fileMOEAD_OCD_HV)

fileHarmony_MOEAD_NONE<-paste(resultDirectory, "Harmony_MOEAD_NONE", sep="/")
fileHarmony_MOEAD_NONE<-paste(fileHarmony_MOEAD_NONE, problem, sep="/")
fileHarmony_MOEAD_NONE<-paste(fileHarmony_MOEAD_NONE, indicator, sep="/")
Harmony_MOEAD_NONE<-scan(fileHarmony_MOEAD_NONE)

fileHarmony_MOEAD_MOVING_AVG<-paste(resultDirectory, "Harmony_MOEAD_MOVING_AVG", sep="/")
fileHarmony_MOEAD_MOVING_AVG<-paste(fileHarmony_MOEAD_MOVING_AVG, problem, sep="/")
fileHarmony_MOEAD_MOVING_AVG<-paste(fileHarmony_MOEAD_MOVING_AVG, indicator, sep="/")
Harmony_MOEAD_MOVING_AVG<-scan(fileHarmony_MOEAD_MOVING_AVG)

fileHarmony_MOEAD_MGBM<-paste(resultDirectory, "Harmony_MOEAD_MGBM", sep="/")
fileHarmony_MOEAD_MGBM<-paste(fileHarmony_MOEAD_MGBM, problem, sep="/")
fileHarmony_MOEAD_MGBM<-paste(fileHarmony_MOEAD_MGBM, indicator, sep="/")
Harmony_MOEAD_MGBM<-scan(fileHarmony_MOEAD_MGBM)

fileHarmony_MOEAD_MGBM2<-paste(resultDirectory, "Harmony_MOEAD_MGBM2", sep="/")
fileHarmony_MOEAD_MGBM2<-paste(fileHarmony_MOEAD_MGBM2, problem, sep="/")
fileHarmony_MOEAD_MGBM2<-paste(fileHarmony_MOEAD_MGBM2, indicator, sep="/")
Harmony_MOEAD_MGBM2<-scan(fileHarmony_MOEAD_MGBM2)

fileHarmony_MOEAD_OCD_HV<-paste(resultDirectory, "Harmony_MOEAD_OCD_HV", sep="/")
fileHarmony_MOEAD_OCD_HV<-paste(fileHarmony_MOEAD_OCD_HV, problem, sep="/")
fileHarmony_MOEAD_OCD_HV<-paste(fileHarmony_MOEAD_OCD_HV, indicator, sep="/")
Harmony_MOEAD_OCD_HV<-scan(fileHarmony_MOEAD_OCD_HV)

fileHarmony_Hybrid_MOEAD_NONE<-paste(resultDirectory, "Harmony_Hybrid_MOEAD_NONE", sep="/")
fileHarmony_Hybrid_MOEAD_NONE<-paste(fileHarmony_Hybrid_MOEAD_NONE, problem, sep="/")
fileHarmony_Hybrid_MOEAD_NONE<-paste(fileHarmony_Hybrid_MOEAD_NONE, indicator, sep="/")
Harmony_Hybrid_MOEAD_NONE<-scan(fileHarmony_Hybrid_MOEAD_NONE)

fileHarmony_Hybrid_MOEAD_MOVING_AVG<-paste(resultDirectory, "Harmony_Hybrid_MOEAD_MOVING_AVG", sep="/")
fileHarmony_Hybrid_MOEAD_MOVING_AVG<-paste(fileHarmony_Hybrid_MOEAD_MOVING_AVG, problem, sep="/")
fileHarmony_Hybrid_MOEAD_MOVING_AVG<-paste(fileHarmony_Hybrid_MOEAD_MOVING_AVG, indicator, sep="/")
Harmony_Hybrid_MOEAD_MOVING_AVG<-scan(fileHarmony_Hybrid_MOEAD_MOVING_AVG)

fileHarmony_Hybrid_MOEAD_MGBM<-paste(resultDirectory, "Harmony_Hybrid_MOEAD_MGBM", sep="/")
fileHarmony_Hybrid_MOEAD_MGBM<-paste(fileHarmony_Hybrid_MOEAD_MGBM, problem, sep="/")
fileHarmony_Hybrid_MOEAD_MGBM<-paste(fileHarmony_Hybrid_MOEAD_MGBM, indicator, sep="/")
Harmony_Hybrid_MOEAD_MGBM<-scan(fileHarmony_Hybrid_MOEAD_MGBM)

fileHarmony_Hybrid_MOEAD_MGBM2<-paste(resultDirectory, "Harmony_Hybrid_MOEAD_MGBM2", sep="/")
fileHarmony_Hybrid_MOEAD_MGBM2<-paste(fileHarmony_Hybrid_MOEAD_MGBM2, problem, sep="/")
fileHarmony_Hybrid_MOEAD_MGBM2<-paste(fileHarmony_Hybrid_MOEAD_MGBM2, indicator, sep="/")
Harmony_Hybrid_MOEAD_MGBM2<-scan(fileHarmony_Hybrid_MOEAD_MGBM2)

fileHarmony_Hybrid_MOEAD_OCD_HV<-paste(resultDirectory, "Harmony_Hybrid_MOEAD_OCD_HV", sep="/")
fileHarmony_Hybrid_MOEAD_OCD_HV<-paste(fileHarmony_Hybrid_MOEAD_OCD_HV, problem, sep="/")
fileHarmony_Hybrid_MOEAD_OCD_HV<-paste(fileHarmony_Hybrid_MOEAD_OCD_HV, indicator, sep="/")
Harmony_Hybrid_MOEAD_OCD_HV<-scan(fileHarmony_Hybrid_MOEAD_OCD_HV)

algs<-c("MOEAD_NONE","MOEAD_MOVING_AVG","MOEAD_MGBM","MOEAD_MGBM2","MOEAD_OCD_HV","Harmony_MOEAD_NONE","Harmony_MOEAD_MOVING_AVG","Harmony_MOEAD_MGBM","Harmony_MOEAD_MGBM2","Harmony_MOEAD_OCD_HV","Harmony_Hybrid_MOEAD_NONE","Harmony_Hybrid_MOEAD_MOVING_AVG","Harmony_Hybrid_MOEAD_MGBM","Harmony_Hybrid_MOEAD_MGBM2","Harmony_Hybrid_MOEAD_OCD_HV")
boxplot(MOEAD_NONE,MOEAD_MOVING_AVG,MOEAD_MGBM,MOEAD_MGBM2,MOEAD_OCD_HV,Harmony_MOEAD_NONE,Harmony_MOEAD_MOVING_AVG,Harmony_MOEAD_MGBM,Harmony_MOEAD_MGBM2,Harmony_MOEAD_OCD_HV,Harmony_Hybrid_MOEAD_NONE,Harmony_Hybrid_MOEAD_MOVING_AVG,Harmony_Hybrid_MOEAD_MGBM,Harmony_Hybrid_MOEAD_MGBM2,Harmony_Hybrid_MOEAD_OCD_HV,names=algs, notch = TRUE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(2,3))
indicator<-"IGD"
qIndicator(indicator, "UF1")
qIndicator(indicator, "UF2")
qIndicator(indicator, "UF3")
qIndicator(indicator, "UF4")
qIndicator(indicator, "UF5")
qIndicator(indicator, "UF6")
qIndicator(indicator, "UF7")
qIndicator(indicator, "UF8")
qIndicator(indicator, "UF9")
qIndicator(indicator, "UF10")
