
data = csvread("event_breakdown_by_member_FR_30days.csv");

% Select 100,000 random members
%random_rows = randperm(size(data,1), 100000);
%data = data(random_rows,:);

%data = csvread("event_breakdown_by_member_30days.csv");
% 'member_id', 'ContactImportedEvent', 'ContactInvitedEvent', 'DirectContactRequestCreatedEvent', 'FollowCompanyEvent', 'LetsMeetOpenStatusUpdatedEvent', 'LetsMeetPropositionAcceptedEvent', 'LoginLoggedEvent', 'MemberUsedTheLetsMeetAppEvent', 'NewsCreatedEvent', 'PositionCreatedEvent', 'ProfilePictureCreatedEvent', 'ProfileVisitedEvent', 'SkillEndorsedCreatedEvent'

column_number = 13; % 'ProfileVisitedEvent'

premium = csvread("premium_members.csv"); % 'member_id' 

visits = csvread("profiles_visited_30days.csv");

% Split events between premium and non-premium

[x,data_premium_idx,premium_idx] = intersect(data(:,1),premium);
[x,data_non_premium_idx] = setdiff(data(:,1), premium);

data_premium = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);

% Ignore visits for members who have been inactive in the last 30 days

[x, data_idx, visits_active_idx] = intersect(data(:,1),visits(:,1));
visits_active = visits(visits_active_idx, :);

% Split visits between premium and non-premium

[x,visits_premium_idx,premium_idx] = intersect(visits_active(:,1),premium);
[x,visits_non_premium_idx] = setdiff(visits_active(:,1), premium);

visits_premium     = visits_active(visits_premium_idx,:);
visits_non_premium = visits_active(visits_non_premium_idx, :);

% Find visits for premium members

[x,visits_premium_idx,data_idx] = intersect(visits_premium(:,1),data_premium(:,1));
visits_premium(:,3) = zeros(length(visits_premium),1);
visits_premium(visits_premium_idx, 3) = data_premium(data_idx, column_number);

% Find visits for non-premium members

[x,visits_non_premium_idx,data_idx] = intersect(visits_non_premium(:,1),data_non_premium(:,1));
visits_non_premium(:,3) = zeros(length(visits_non_premium),1);
visits_non_premium(visits_non_premium_idx, 3) = data_non_premium(data_idx, column_number);

figure(1);
scatter(visits_premium(:,2),visits_premium(:,3), 4, 'r')
xlabel('Logins');
ylabel('Visits');
title('Distribution of premium members : logins vs visits (30 days)');
axis([0 500 0 500])
print("logins_vs_visits_premium_30days.jpg")

figure(2);
scatter(visits_non_premium(:,2),visits_non_premium(:,3), 4, 'b')
xlabel('Logins');
ylabel('Visits');
title('Distribution of non-premium members : logins vs visits (30 days)');
axis([0 500 0 500])
print("logins_vs_visits_non_premium_30days.jpg")

figure(3);
scatter(visits_premium(:,2),visits_premium(:,3), 4, 'r')
hold on;
scatter(visits_non_premium(:,2),visits_non_premium(:,3), 4, 'b')
xlabel('Logins');
ylabel('Visits');
title('Distribution of all members : logins vs visits (30 days)');
axis([0 500 0 500])
print("logins_vs_visits_30days.jpg")


