package de.jonasborn.gustav.ludwig

class Predict extends de.jonasborn.gustav.api.ParameterList {


    /**
     * usage: ludwig predict [options]
     *
     * This script loads a pretrained model and uses it to predict
     *
     * optional arguments:
     *   -h, --help            show this help message and exit
     *   --data_csv DATA_CSV   input data CSV file. If it has a split column, it will
     *                         be used for splitting (0: train, 1: validation, 2:
     *                         test), otherwise the dataset will be randomly split
     *   --data_hdf5 DATA_HDF5
     *                         input data HDF5 file. It is an intermediate preprocess
     *                         version of the input CSV created the first time a CSV
     *                         file is used in the same directory with the same name
     *                         and a hdf5 extension
     *   --train_set_metadata_json TRAIN_SET_METADATA_JSON
     *                         input metadata JSON file. It is an intermediate
     *                         preprocess file containing the mappings of the input
     *                         CSV created the first time a CSV file is used in the
     *                         same directory with the same name and a json extension
     *   -s {training,validation,test,full}, --split {training,validation,test,full}*                         the split to test the model on
     *   -m MODEL_PATH, --model_path MODEL_PATH
     *                         model to load
     *   -od OUTPUT_DIRECTORY, --output_directory OUTPUT_DIRECTORY
     *                         directory that contains the results
     *   -ssuo, --skip_save_unprocessed_output
     *                         skips saving intermediate NPY output files
     *   -sstp, --skip_save_test_predictions
     *                         skips saving test predictions CSV files
     *   -sstes, --skip_save_test_statistics
     *                         skips saving test statistics JSON file
     *   -bs BATCH_SIZE, --batch_size BATCH_SIZE
     *                         size of batches
     *   -g GPUS, --gpus GPUS  list of gpu to use
     *   -gf GPU_FRACTION, --gpu_fraction GPU_FRACTION
     *                         fraction of gpu memory to initialize the process with
     *   -uh, --use_horovod    uses horovod for distributed training
     *   -dbg, --debug         enables debugging mode
     *   -l {critical,error,warning,info,debug,notset}, --logging_level {critical,error,warning,info,debug,notset}*                         the level of logging to use
     */

    Predict() {
        super("--")
        norm(Boolean.class, "help", "show this help message and exit")
        norm(File.class, "data_csv", "input data CSV file. If it has a split column, it will be used for splitting (0: train, 1: validation, 2: test), otherwise the dataset will be randomly split")
        norm(String.class, "data_hdf5", "input data HDF5 file. It is an intermediate preprocess version of the input CSV created the first time a CSV file is used in the same directory with the same name and a hdf5 extension")
        norm(String.class, "train_set_metadata_json", "input metadata JSON file. It is an intermediate preprocess file containing the mappings of the input CSV created the first time a CSV file is used in the same directory with the same name and a json extension")
        en(Split.class, "split", "the split to test the model on")
        norm(File.class, "model_path", "model to load")
        norm(File.class, "output_directory", "directory that contains the results")
        norm(Boolean.class, "skip_save_unprocessed_output", "skips saving intermediate NPY output files")
        norm(Boolean.class, "skip_save_test_predictions", "skips saving test predictions CSV files")
        norm(Boolean.class, "skip_save_test_statistics", "skips saving test statistics JSON file")
        norm(Long.class, "batch_size", "size of batches")
        norm(String.class, "gpus", "list of gpu to use")
        norm(String.class, "gpu_fraction", "fraction of gpu memory to initialize the process with")
        norm(Boolean.class, "use_horovod", "uses horovod for distributed training")
        norm(Boolean.class, "debug", "enables debugging mode")
        list(LogLevel.class, "logging_level", "the level of logging to use")
    }

}
