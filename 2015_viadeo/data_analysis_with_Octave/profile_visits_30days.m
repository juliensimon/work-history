
data = csvread("event_breakdown_by_member_30days.csv");
% 'member_id', 'ContactImportedEvent', 'ContactInvitedEvent', 'DirectContactRequestCreatedEvent', 'FollowCompanyEvent', 'LetsMeetOpenStatusUpdatedEvent', 'LetsMeetPropositionAcceptedEvent', 'LoginLoggedEvent', 'MemberUsedTheLetsMeetAppEvent', 'NewsCreatedEvent', 'PositionCreatedEvent', 'ProfilePictureCreatedEvent', 'ProfileVisitedEvent', 'SkillEndorsedCreatedEvent'

% Select 10,000 random members

random_rows = randperm(size(data,1), 10000);
data = data(random_rows,:);

premium = csvread("premium_members.csv"); % 'member_id' 

visits = csvread("profiles_visited_30days.csv");

% Split events between premium and non-premium

[x,data_premium_idx,premium_idx] = intersect(data(:,1),premium);
[x,data_non_premium_idx] = setdiff(data(:,1), premium);

data_premium = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);

% Ignore visits for members who have been inactive in the last 30 days

[x, visits_active_idx, data_idx] = intersect(data(:,1),visits(:,1));
visits_active = visits(visits_active_idx, :);

% Split visits between premium and non-premium

[x,visits_premium_idx,premium_idx] = intersect(visits_active(:,1),premium);
[x,visits_non_premium_idx] = setdiff(visits_active(:,1), premium);

visits_premium     = visits_active(visits_premium_idx,:);
visits_non_premium = visits_active(visits_non_premium_idx, :);

% Histogram for premium
figure(1)
hist(visits_premium(:,2), 0:1:100, 100, 'facecolor', 'r');
xlabel("Number of visits");
ylabel("Percentage");
title("Normalized distribution of profile views for premium members");
axis([0 100 0 40]);
print("profile_visits_premium_30days.jpg");

% Histogram for non premium
figure(2)
hist(visits_non_premium(:,2), 0:1:100, 100, 'facecolor', 'b');
xlabel("Number of visits");
ylabel("Percentage");
title("Normalized distribution of profile views for non-premium members");
axis([0 100 0 40]);
print("profile_visits_non_premium_30days.jpg");

% Histogram for all
figure(3)
hist(visits_non_premium(:,2), 0:1:100, 100, 'facecolor', 'b');
hold on;
hist(visits_premium(:,2), 0:1:100, 100, 'facecolor', 'r');
xlabel("Number of visits");
ylabel("Percentage");
title("Normalized distribution of profile views for all members");
axis([0 100 0 40]);
print("profile_visits_30days.jpg");

