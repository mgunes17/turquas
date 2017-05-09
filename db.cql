CREATE TABLE W2V_TOKEN (
    token_name text,
    is_stem boolean,
    value list<float>,
    primary key(is_stem, token_name)
);

CREATE TABLE source (
    source_name text PRIMARY KEY,
    best_words set<text>,
    last_updated_date timestamp,
    word_count_map map<text, int>
);

CREATE TABLE unique_word (
    word text PRIMARY KEY,
    source set<text>,
    value map<text, double>
);

CREATE TABLE token_morph_analysis (
    isanalysisnull boolean,
    token_name text,
    analysis set<text>,
    PRIMARY KEY (isanalysisnull, token_name)
);

CREATE TABLE sentence (
    original_sentence text PRIMARY KEY,
    questions set<text>,
    stemmed_words_list list<text>,
    tags set<text>,
    token_list list<text>
);