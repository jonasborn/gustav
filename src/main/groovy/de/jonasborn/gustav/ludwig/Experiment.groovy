package de.jonasborn.gustav.ludwig

class Experiment extends de.jonasborn.gustav.api.ParameterList {

    public Experiment() {
        super("--")
        norm(Boolean, "help", "show this help message and exit")
        norm(File, "output_directory", "directory that contains the results")
        norm(String, "experiment_name", "experiment name")
        norm(Boolean, "data_csv", "input data CSV file. If it has a split column, it will")
        norm(String, "model_name", "name for the model be used for splitting (0: train, 1: validation, 2: test), otherwise the dataset will be randomly split")
        norm(File, "data_train_csv", "input train data CSV file")
        norm(File, "data_validation_csv", "input validation data CSV file")
        norm(File, "data_test_csv", "input test data CSV file")
        norm(File, "data_hdf5", "input data HDF5 file. It is an intermediate preprocess version of the input CSV created the first time a CSV file is used in the same directory with the same name and a hdf5 extension")
        norm(File, "data_train_hdf5", "input train data HDF5 file. It is an intermediate preprocess version of the input CSV created the first time a CSV file is used in the same directory with the same name and a hdf5 extension")
        norm(File, "data_validation_hdf5", "input validation data HDF5 file. It is an intermediate")
        norm(File, "data_test_hdf5", "input test data HDF5 file. It is an intermediate")
        norm(String, "metadata_json", "input metadata JSON file. It is an intermediate preprocess file containing the mappings of the input CSV created the first time a CSV file is used in the same directory with the same name and a json extension")
        norm(Boolean, "skip_save_processed_input", "skips saving intermediate HDF5 and JSON files")
        norm(Boolean, "skip_save_unprocessed_output", "skips saving intermediate NPY output files")
        norm(String, "model_definition", "model definition")
        norm(String, "model_definition_file", "YAML file describing the model. Ignores --model_hyperparameters")
        norm(File, "model_load_path", "path of a pretrained model to load as initialization")
        norm(File, "model_resume_path", "path of a the model directory to resume training of")
        norm(Boolean, "skip_save_training_description", "disables saving the description JSON file")
        norm(Boolean, "skip_save_training_statistics", "disables saving training statistics JSON file")
        norm(Boolean, "skip_save_test_predictions", "skips saving test predictions CSV files")
        norm(Boolean, "skip_save_test_statistics", "skips saving test statistics JSON file")
        norm(Boolean, "skip_save_model", "disables saving model weights and hyperparameters each time the model imrpoves. By default Ludwig saves model weights after each epoch the validation measure imrpvoes, but if the model is really big that can be time consuming if you do not want to keep the weights and just find out what performance can a model get with a set of hyperparameters, use this parameter to skip it,but the model will not be loadable later on")
        norm(Boolean, "skip_save_progress", "disables saving progress each epoch. By default Ludwig saves weights and stats after each epoch for enabling resuming of training, but if the model is really big that can be time consuming and will uses twice as much space, use this parameter to skip it, but training cannot be resumed later on")
        norm(Boolean, "skip_save_log", "disables saving TensorBoard logs. By default Ludwig saves logs for the TensorBoard, but if it is not needed turning it off can slightly increase the overall speed")
        list(String, "random_seed", "a random seed that is going to be used anywhere there is a call to a random number generator: data splitting, parameter initialization and training set shuffling")
        list(String, "gpus", "list of GPUs to use")
        norm(Boolean, "use_horovod", "uses horovod for distributed training")
        norm(Boolean, "debug", "enables debugging mode")
        en(String, "gpu_fraction", "fraction of gpu memory to initialize the process with")
    }

    /**
     *   --data_csv DATA_CSV   input data CSV file. If it has a split column, it will
     *                         be used for splitting (0: train, 1: validation, 2:
     *                         test), otherwise the dataset will be randomly split
     *   --data_train_csv DATA_TRAIN_CSV
     *                         input train data CSV file
     *   --data_validation_csv DATA_VALIDATION_CSV
     *                         input validation data CSV file
     *   --data_test_csv DATA_TEST_CSV
     *                         input test data CSV file
     *   --data_hdf5 DATA_HDF5
     *                         input data HDF5 file. It is an intermediate preprocess
     *                         version of the input CSV created the first time a CSV
     *                         file is used in the same directory with the same name
     *                         and a hdf5 extension
     *   --data_train_hdf5 DATA_TRAIN_HDF5
     *                         input train data HDF5 file. It is an intermediate
     *                         preprocess version of the input CSV created the first
     *                         time a CSV file is used in the same directory with the
     *                         same name and a hdf5 extension
     *   --data_validation_hdf5 DATA_VALIDATION_HDF5
     *                         input validation data HDF5 file. It is an intermediate
     *                         preprocess version of the input CSV created the first
     *                         time a CSV file is used in the same directory with the
     *                         same name and a hdf5 extension
     *   --data_test_hdf5 DATA_TEST_HDF5
     *                         input test data HDF5 file. It is an intermediate
     *                         preprocess version of the input CSV created the first
     *                         time a CSV file is used in the same directory with the
     *                         same name and a hdf5 extension
     *   --metadata_json METADATA_JSON
     *                         input metadata JSON file. It is an intermediate
     *                         preprocess file containing the mappings of the input
     *                         CSV created the first time a CSV file is used in the
     *                         same directory with the same name and a json extension
     *   -sspi, --skip_save_processed_input
     *                         skips saving intermediate HDF5 and JSON files
     *   -ssuo, --skip_save_unprocessed_output
     *                         skips saving intermediate NPY output files
     *   -md MODEL_DEFINITION, --model_definition MODEL_DEFINITION
     *                         model definition
     *   -mdf MODEL_DEFINITION_FILE, --model_definition_file MODEL_DEFINITION_FILE
     *                         YAML file describing the model. Ignores
     *                         --model_hyperparameters
     *   -mlp MODEL_LOAD_PATH, --model_load_path MODEL_LOAD_PATH
     *                         path of a pretrained model to load as initialization
     *   -mrp MODEL_RESUME_PATH, --model_resume_path MODEL_RESUME_PATH
     *                         path of a the model directory to resume training of
     *   -sstd, --skip_save_training_description
     *                         disables saving the description JSON file
     *   -ssts, --skip_save_training_statistics
     *                         disables saving training statistics JSON file
     *   -sstp, --skip_save_test_predictions
     *                         skips saving test predictions CSV files
     *   -sstes, --skip_save_test_statistics
     *                         skips saving test statistics JSON file
     *   -ssm, --skip_save_model
     *                         disables saving model weights and hyperparameters each
     *                         time the model imrpoves. By default Ludwig saves model
     *                         weights after each epoch the validation measure
     *                         imrpvoes, but if the model is really big that can be
     *                         time consuming if you do not want to keep the weights
     *                         and just find out what performance can a model get
     *                         with a set of hyperparameters, use this parameter to
     *                         skip it,but the model will not be loadable later on
     *   -ssp, --skip_save_progress
     *                         disables saving progress each epoch. By default Ludwig
     *                         saves weights and stats after each epoch for enabling
     *                         resuming of training, but if the model is really big
     *                         that can be time consuming and will uses twice as much
     *                         space, use this parameter to skip it, but training
     *                         cannot be resumed later on
     *   -ssl, --skip_save_log
     *                         disables saving TensorBoard logs. By default Ludwig
     *                         saves logs for the TensorBoard, but if it is not
     *                         needed turning it off can slightly increase the
     *                         overall speed
     *   -rs RANDOM_SEED, --random_seed RANDOM_SEED
     *                         a random seed that is going to be used anywhere there
     *                         is a call to a random number generator: data
     *                         splitting, parameter initialization and training set
     *                         shuffling
     *   -g GPUS [GPUS ...], --gpus GPUS [GPUS ...]
     *                         list of GPUs to use
     *   -gf GPU_FRACTION, --gpu_fraction GPU_FRACTION
     *                         fraction of gpu memory to initialize the process with
     *   -uh, --use_horovod    uses horovod for distributed training
     *   -dbg, --debug         enables debugging mode
     *   -l {critical,error,warning,info,debug,notset}, --logging_level {critical,error,warning,info,debug,notset}*                         the level of logging to use
     */

}
