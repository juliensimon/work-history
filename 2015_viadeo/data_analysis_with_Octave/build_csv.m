
% ======= Load data files

% 'member_id', 'ContactImportedEvent', 'ContactInvitedEvent', 'DirectContactRequestCreatedEvent', 'FollowCompanyEvent', 'LetsMeetOpenStatusUpdatedEvent', 'LetsMeetPropositionAcceptedEvent', 'LoginLoggedEvent', 'MemberUsedTheLetsMeetAppEvent', 'NewsCreatedEvent', 'PositionCreatedEvent', 'ProfilePictureCreatedEvent', 'ProfileVisitedEvent', 'SkillEndorsedCreatedEvent'
data    = csvread("event_breakdown_by_member_FR_30days.csv");

% 'member_id', 'number of visits on profile'
visits  = csvread("profiles_visited_30days.csv");

% 'premium member_ids'
premium = csvread("premium_members.csv");

% ======= Select random members

%random_samples = 20000;
%random_rows = randperm(size(data,1), random_samples);
%data = data(random_rows,:);

% ======= Prepare new data 

nb_columns = 5; % member_id, logins, visits on profile, news, dcr
data_filtered = zeros(size(data,1), nb_columns+1); % one extra column for premium status

% Add member_id
data_filtered(:,1) = data(:,1);

% Add logins
data_filtered(:,2) = data(:,8);

% Add visits on profile
[x, data_idx, visits_idx] = intersect(data_filtered(:,1),visits(:,1));
data_filtered(data_idx,3) = visits(visits_idx, 2);

% Add news 
data_filtered(:,4) = data(:,10);

% Add DCR
data_filtered(:,5) = data(:,4);

% Add premium status
[x,data_premium_idx,premium_idx] = intersect(data_filtered(:,1),premium);
[x,data_non_premium_idx] = setdiff(data_filtered(:,1), premium);

data_filtered(data_premium_idx,     nb_columns+1) = 1;
data_filtered(data_non_premium_idx, nb_columns+1) = 0;

% ======= Show stats

fprintf("Total members       : %d\n", size(data_filtered,1));
fprintf("Non-premium members : %d\n", size(data_non_premium_idx,1));
fprintf("Premium members     : %d\n\n", size(data_premium_idx,1));

fprintf("Means for all members\n");           mean(data_filtered(:,2:nb_columns))
fprintf("Stddevs for all members\n");         std(data_filtered(:,2:nb_columns))

% ======= Remove outliers

% logins > 3000
outliers_idx = find(data_filtered(:,2)>3000);
fprintf("Removing %d outliers for logins\n", size(outliers_idx, 1));
data_filtered(outliers_idx, :) = [];

% visits > 3000
outliers_idx = find(data_filtered(:,3)>3000);
fprintf("Removing %d outliers for visits on profile\n", size(outliers_idx, 1));
data_filtered(outliers_idx, :) = [];

% news > 1000
outliers_idx = find(data_filtered(:,4)>1000);
fprintf("Removing %d outliers for news\n", size(outliers_idx, 1));
data_filtered(outliers_idx, :) = [];

% dcr > 3000
outliers_idx = find(data_filtered(:,5)>3000);
fprintf("Removing %d outliers for dcr\n", size(outliers_idx, 1));
data_filtered(outliers_idx, :) = [];

% ======= Normalize data

data_filtered_norm = zeros(size(data_filtered, 1), size(data_filtered, 2));
data_filtered_norm(:,nb_columns+1) = data_filtered(:,nb_columns+1); % store premium status

% Compute mean and stddev for each column
fprintf("\nMeans for all members\n");         means=mean(data_filtered(:,2:nb_columns))
fprintf("Stddevs for all members\n");         devs=std(data_filtered(:,2:nb_columns))

% Extend mean and stddev vectors to matrices
means_mat = repmat(means, size(data_filtered,1), 1);
devs_mat  = repmat(devs,  size(data_filtered,1), 1);

% Normalize columns (except member_id and premimum status)
data_filtered_norm(:,2:nb_columns) = (data_filtered(:,2:nb_columns)-means_mat) ./ devs_mat;

% Sanity check
fprintf("means must be 0\n");
mean(data_filtered_norm(:, 2:nb_columns))

% ======= Save result

% Save data file with all columns --> for reference
%csvwrite('dataset_reference.csv', data_filtered);

% Save normalized data file, without first column (member_id) --> for model training
%csvwrite('dataset_norm.csv', data_filtered_norm(:, 2:nb_columns+1));

% Save normalized data file, without first column (member_id) and last column (premium) --> for prediction
%csvwrite('dataset_topredict.csv', data_filtered(:, 2:nb_columns));

