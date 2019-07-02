library(data.table);
setwd("~/NetBeansProjects/ArvoreDeDecisao/datasets/titanic")
train<-read.csv('train.csv',stringsAsFactors=FALSE)
test<-read.csv('test.csv',stringsAsFactors=FALSE)

extractFeatures <- function(data, istrain=TRUE) {
    features <- c("Pclass",
                  "Sex",
                  "Age",
                  "SibSp",
                  "Parch",
                  "Fare",
                  "Cabin",
                  "Embarked")
    fea <- data[,features]
    fea$Sex[is.na(fea$Sex)] <- "female"
    fea$Embarked[fea$Embarked==""] = "S"
    fea$Sex <- fea$Sex[fea$Sex == "male"] <- "1"
    fea$Sex <- fea$Sex[fea$Sex == "female"] <- "0"
    fea$Sex<-as.numeric(as.character(fea$Sex))
    fea$Embarked[fea$Embarked=="S"] <- "0"
    fea$Embarked[fea$Embarked=="Q"] <- "1"
    fea$Embarked[fea$Embarked=="C"] <- "2"
    
    fea[substr(fea$Cabin,0,1)=="A", "Cabin"] <-"1"
    fea[substr(fea$Cabin,0,1)=="B", "Cabin"] <-"2"
    fea[substr(fea$Cabin,0,1)=="C", "Cabin"] <-"3"
    fea[substr(fea$Cabin,0,1)=="D", "Cabin"] <-"4"
    fea[substr(fea$Cabin,0,1)=="E", "Cabin"] <-"5"
    fea[substr(fea$Cabin,0,1)=="F", "Cabin"] <-"6"
    fea[substr(fea$Cabin,0,1)=="G", "Cabin"] <-"7"
    fea[substr(fea$Cabin,0,1)=="T", "Cabin"] <-"8"
    fea[length(fea$Cabin)==0, "Cabin"] <-"0"
    
    fea$Cabin<-as.numeric(fea$Cabin)
    
    fea[is.na(fea)] <- 0
    fea[] <- lapply(fea, function(x) as.numeric(as.character(x)))
    
    if(istrain)
        fea$Survived <- as.numeric(data$Survived)
    return(fea)
}

xtrain <-extractFeatures(train,TRUE)
xtest <-extractFeatures(test,FALSE)
fwrite(xtrain, file = "reshaped_train.csv")
fwrite(xtest, file = "reshaped_test.csv")
