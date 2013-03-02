class Blast < ActiveRecord::Base
  belongs_to :owner, :class_name => "User", :foreign_key => :owner_id
  belongs_to :entry, :class_name => "Content", :foreign_key => :entry_id
  belongs_to :output, :class_name => "Content", :foreign_key => :output_id
  
  attr_accessible :entry_id, :database, :end_at, :expect, :gap_costs, :m_scores, 
                  :max_matches_range, :max_target_sequence, :output_id,
                  :owner_id, :program, :status, :start_at, :title, :word_size
                  
  validates_uniqueness_of :title, :scope => :owner_id                
                  
end
