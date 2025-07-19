
data = csvread("event_breakdown_by_member_30days.csv");
% 'member_id', 'ContactImportedEvent', 'ContactInvitedEvent', 'DirectContactRequestCreatedEvent', 'FollowCompanyEvent', 'LetsMeetOpenStatusUpdatedEvent', 'LetsMeetPropositionAcceptedEvent', 'LoginLoggedEvent', 'MemberUsedTheLetsMeetAppEvent', 'NewsCreatedEvent', 'PositionCreatedEvent', 'ProfilePictureCreatedEvent', 'ProfileVisitedEvent', 'SkillEndorsedCreatedEvent'

premium = csvread("premium_members.csv");
% 'member_id'

[x,data_premium_idx,premium_idx] = intersect(data(:,1),premium);
[x,data_non_premium_idx] = setdiff(data(:,1), premium);

data_premium = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);

contactsImp 		= data(:,2);
contactsInv 		= data(:,3);
dcrCreated 		= data(:,4);
followCompany 		= data(:,5);
letsMeetStatus 		= data(:,6);
letsMeetAccepted 	= data(:,7);
logins			= data(:,8); 
letsMeetApp 		= data(:,9);
newsCreated 		= data(:,10);
positionCreated 	= data(:,11);
pictureCreated 		= data(:,12);
profiles		= data(:,13); 
skillEndorsed 		= data(:,14);

% LOGINS VS PROFILE VIEWS

logins_premium		= data_premium(:,8); 
profiles_premium	= data_premium(:,13); 

logins_non_premium	= data_non_premium(:,8); 
profiles_non_premium	= data_non_premium(:,13); 

figure(1);
scatter(logins_premium,profiles_premium, 1, 'r')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of premium members : logins vs profile views (30 days)');
axis([0 1000 0 1000])
print("logins_vs_profile_views_premium_30days.jpg")

figure(2);
scatter(logins_non_premium,profiles_non_premium, 1, 'b')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of non-premium members : logins vs profile views (30 days)');
axis([0 1000 0 1000])
print("logins_vs_profile_views_non_premium_30days.jpg")

figure(3);
scatter(logins_premium,profiles_premium, 1, 'r')
hold on;
scatter(logins_non_premium,profiles_non_premium, 1, 'b')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of all members : logins vs profile views (30 days)');
axis([0 1000 0 1000])
print("logins_vs_profile_views_30days.jpg")

figure(4);
scatter(logins_premium,profiles_premium, 1, 'r')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of premium members : logins vs profile views (30 days)');
axis([0 500 0 500])
print("logins_vs_profile_views_premium2_30days.jpg")

figure(5);
scatter(logins_non_premium,profiles_non_premium, 1, 'b')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of non-premium members : logins vs profile views (30 days)');
axis([0 500 0 500])
print("logins_vs_profile_views_non_premium2_30days.jpg")

figure(6);
scatter(logins_premium,profiles_premium, 1, 'r')
hold on;
scatter(logins_non_premium,profiles_non_premium, 1, 'b')
xlabel('Logins');
ylabel('Profiles views');
title('Distribution of all members : logins vs profile views (30 days)');
axis([0 500 0 500])
print("logins_vs_profile_views2_30days.jpg")

