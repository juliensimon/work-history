data 	= csvread("events_by_member_30days.csv");
premium = csvread("premium_members.csv");

[x,data_premium_idx,premium_idx]  = intersect(data(:,1),premium);
[x,data_non_premium_idx]          = setdiff(data(:,1), premium);

data_premium 	 = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);

vect_premium     = data_premium(:,2)';
vect_non_premium = data_non_premium(:,2)';

figure(1);
hist(vect_non_premium, 0:5:300, 100, 'facecolor', 'b');
xlabel('Event count');
ylabel('Percentage');
title('Distribution of events per non-premium member over 30 days');
axis([-1 300 0 15])
print("events_per_member_non_premium_30days.jpg")
 
figure(2);
hist(vect_premium, 0:5:300, 100, 'facecolor', 'r');
xlabel('Event count');
ylabel('Percentage');
title('Distribution of events per premium member over 30 days');
axis([-1 300 0 15])
print("events_per_member_premium_30days.jpg")

figure(3);
hist(vect_non_premium, 0:5:300, 100, 'facecolor', 'b');
hold on;
hist(vect_premium, 0:5:300, 100, 'facecolor', 'r');
xlabel('Event count');
ylabel('Percentage');
title('Distribution of events per premium member over 30 days');
axis([-1 300 0 15])
print("events_per_member_30days.jpg")
