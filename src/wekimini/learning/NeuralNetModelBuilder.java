/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.learning;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import wekimini.LearningModelBuilder;
import wekimini.WekaModelBuilderHelper;
import wekimini.osc.OSCNumericOutput;
import wekimini.osc.OSCOutput;

/**
 *
 * @author rebecca
 */
public class NeuralNetModelBuilder implements LearningModelBuilder {
    private transient Instances trainingData = null;
    private transient Classifier classifier = null;
        
    
    public NeuralNetModelBuilder() {
        classifier = new MultilayerPerceptron();
        ((MultilayerPerceptron)classifier).setHiddenLayers("i");
    }
    
    @Override
    public void setTrainingExamples(Instances examples) {
        trainingData = examples;
    }

    @Override
    public Model build(String name) throws Exception {
       if (trainingData == null) {
           throw new IllegalStateException("Must set training examples (to not null) before building model");
       }
       MultilayerPerceptron m = (MultilayerPerceptron)WekaModelBuilderHelper.build(classifier, trainingData);
       return new NeuralNetworkModel(name, m);
    }

    @Override
    public boolean isCompatible(OSCOutput o) {
        return (o instanceof OSCNumericOutput);
    }
    
    public NeuralNetModelBuilder fromTemplate(ModelBuilder b) {
        if (b instanceof NeuralNetModelBuilder) {
            return new NeuralNetModelBuilder();
        }
        return null;
    }

    @Override
    public String getPrettyName() {
        return "Neural Network";
    }

    @Override
    public NeuralNetEditorPanel getEditorPanel() {
        return new NeuralNetEditorPanel(this);
    }
}
